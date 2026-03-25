package com.example.localpos.modules.hr.service.impl;

import com.example.localpos.enums.AttendanceStatus;
import com.example.localpos.modules.hr.dto.response.AttendanceReportDTO;
import com.example.localpos.modules.hr.dto.response.AttendanceReportEntryDTO;
import com.example.localpos.modules.hr.dto.response.MyScheduleResponseDTO;
import com.example.localpos.modules.hr.entity.WorkSchedule;
import com.example.localpos.modules.hr.repository.WorkScheduleRepository;
import com.example.localpos.modules.hr.service.AttendanceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceServiceImpl implements AttendanceService {

    private final WorkScheduleRepository workScheduleRepository;

    // ── Employee self-service ─────────────────────────────────────────────────

    @Override
    public List<MyScheduleResponseDTO> getMySchedules(Long employeeId) {
        return workScheduleRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::toMyScheduleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MyScheduleResponseDTO getMyTodaySchedule(Long employeeId) {
        return workScheduleRepository
                .findByEmployeeIdAndWorkDate(employeeId, LocalDate.now())
                .map(this::toMyScheduleDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No schedule found for today."));
    }

    @Override
    public List<MyScheduleResponseDTO> getMySchedulesInRange(Long employeeId,
                                                             LocalDate from,
                                                             LocalDate to) {
        return workScheduleRepository
                .findByEmployeeIdAndWorkDateBetween(employeeId, from, to)
                .stream()
                .map(this::toMyScheduleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MyScheduleResponseDTO clockIn(Long employeeId) {
        WorkSchedule ws = getTodayScheduleOrThrow(employeeId);

        if (ws.getCheckInTime() != null) {
            throw new IllegalStateException("You have already clocked in today.");
        }

        ws.setCheckInTime(Instant.now());
        ws.setIsPresent(true);
        return toMyScheduleDTO(workScheduleRepository.save(ws));
    }

    @Override
    @Transactional
    public MyScheduleResponseDTO clockOut(Long employeeId) {
        WorkSchedule ws = getTodayScheduleOrThrow(employeeId);

        if (ws.getCheckInTime() == null) {
            throw new IllegalStateException("You must clock in before clocking out.");
        }
        if (ws.getCheckOutTime() != null) {
            throw new IllegalStateException("You have already clocked out today.");
        }

        ws.setCheckOutTime(Instant.now());
        return toMyScheduleDTO(workScheduleRepository.save(ws));
    }

    // ── Manager reporting ─────────────────────────────────────────────────────

    @Override
    public AttendanceReportDTO getDailyReport(LocalDate date) {
        List<WorkSchedule> schedules = workScheduleRepository.findByWorkDate(date);
        List<AttendanceReportEntryDTO> entries = schedules.stream()
                .map(this::toReportEntry)
                .collect(Collectors.toList());

        return AttendanceReportDTO.builder()
                .date(date)
                .totalScheduled(entries.size())
                .totalPresent(countByStatus(entries, AttendanceStatus.PRESENT))
                .totalLate(countByStatus(entries, AttendanceStatus.LATE))
                .totalAbsent(countByStatus(entries, AttendanceStatus.ABSENT))
                .totalNotYet(countByStatus(entries, AttendanceStatus.NOT_YET))
                .entries(entries)
                .build();
    }

    @Override
    public List<AttendanceReportDTO> getRangeReport(LocalDate from, LocalDate to) {
        return from.datesUntil(to.plusDays(1))
                .map(this::getDailyReport)
                .collect(Collectors.toList());
    }

    @Override
    public List<MyScheduleResponseDTO> getEmployeeAttendanceHistory(Long employeeId,
                                                                    LocalDate from,
                                                                    LocalDate to) {
        return workScheduleRepository
                .findByEmployeeIdAndWorkDateBetween(employeeId, from, to)
                .stream()
                .map(this::toMyScheduleDTO)
                .collect(Collectors.toList());
    }

    // ── Status resolution ─────────────────────────────────────────────────────

    /**
     * Rules:
     * - PRESENT  : clocked in, checkInTime <= shift start time (same day)
     * - LATE     : clocked in, checkInTime > shift start time
        * - ABSENT   : shift start has passed (or workDate in past), never clocked in
        * - NOT_YET  : workDate is in the future, or shift has not started yet today
     */
    private AttendanceStatus resolveStatus(WorkSchedule ws) {
        LocalDate today = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        LocalDate workDate = ws.getWorkDate();
        LocalTime shiftStart = ws.getShift().getStartTime();

        if (workDate.isAfter(today)) {
            return AttendanceStatus.NOT_YET;
        }

        // For today's shift, do not mark absent before the shift start time.
        if (workDate.isEqual(today) && nowTime.isBefore(shiftStart) && ws.getCheckInTime() == null) {
            return AttendanceStatus.NOT_YET;
        }

        if (ws.getCheckInTime() == null) {
            return AttendanceStatus.ABSENT;
        }

        // Compare check-in time against scheduled shift start
        LocalTime checkInLocalTime = ws.getCheckInTime()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();

        return checkInLocalTime.isAfter(shiftStart)
                ? AttendanceStatus.LATE
                : AttendanceStatus.PRESENT;
    }

    /**
     * Minutes late — only meaningful when status == LATE.
     */
    private long resolveMinutesLate(WorkSchedule ws) {
        if (ws.getCheckInTime() == null) return 0;

        LocalTime checkInLocalTime = ws.getCheckInTime()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
        LocalTime shiftStart = ws.getShift().getStartTime();

        if (checkInLocalTime.isAfter(shiftStart)) {
            return java.time.Duration.between(shiftStart, checkInLocalTime).toMinutes();
        }
        return 0;
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    private MyScheduleResponseDTO toMyScheduleDTO(WorkSchedule ws) {
        return MyScheduleResponseDTO.builder()
                .scheduleId(ws.getId())
                .workDate(ws.getWorkDate())
                .shiftId(ws.getShift().getId())
                .shiftName(ws.getShift().getName())
                .shiftStart(ws.getShift().getStartTime())
                .shiftEnd(ws.getShift().getEndTime())
                .isPresent(ws.getIsPresent())
                .checkInTime(ws.getCheckInTime())
                .checkOutTime(ws.getCheckOutTime())
                .status(resolveStatus(ws))
                .build();
    }

    private AttendanceReportEntryDTO toReportEntry(WorkSchedule ws) {
        AttendanceStatus status = resolveStatus(ws);
        long minsLate = resolveMinutesLate(ws);

        return AttendanceReportEntryDTO.builder()
                .scheduleId(ws.getId())
                .workDate(ws.getWorkDate())
                .employeeId(ws.getEmployee().getId())
                .employeeFullName(ws.getEmployee().getFullName())
                .employeeUsername(ws.getEmployee().getUsername())
                .shiftName(ws.getShift().getName())
                .shiftStart(ws.getShift().getStartTime())
                .shiftEnd(ws.getShift().getEndTime())
                .status(status)
                .checkInTime(ws.getCheckInTime())
                .checkOutTime(ws.getCheckOutTime())
                .minutesLate(status == AttendanceStatus.LATE ? minsLate : null)
                .build();
    }

    private long countByStatus(List<AttendanceReportEntryDTO> entries, AttendanceStatus status) {
        return entries.stream().filter(e -> e.getStatus() == status).count();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private WorkSchedule getTodayScheduleOrThrow(Long employeeId) {
        return workScheduleRepository
                .findByEmployeeIdAndWorkDate(employeeId, LocalDate.now())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No schedule found for today. Contact your manager."));
    }
}
