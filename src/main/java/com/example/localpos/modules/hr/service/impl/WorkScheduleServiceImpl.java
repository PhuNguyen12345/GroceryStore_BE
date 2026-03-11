package com.example.localpos.modules.hr.service.impl;

import com.example.localpos.modules.hr.dto.request.WorkScheduleRequestDTO;
import com.example.localpos.modules.hr.dto.response.WorkScheduleResponseDTO;
import com.example.localpos.modules.hr.dto.request.WorkScheduleUpdateDTO;
import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.entity.Shift;
import com.example.localpos.modules.hr.entity.WorkSchedule;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import com.example.localpos.modules.hr.repository.ShiftRepository;
import com.example.localpos.modules.hr.repository.WorkScheduleRepository;
import com.example.localpos.modules.hr.service.WorkScheduleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkScheduleServiceImpl implements WorkScheduleService {

    private final WorkScheduleRepository workScheduleRepository;
    private final EmployeeRepository     employeeRepository;
    private final ShiftRepository        shiftRepository;

    // Mapping

    private WorkScheduleResponseDTO toDTO(WorkSchedule ws) {
        return WorkScheduleResponseDTO.builder()
                .id(ws.getId())
                .employeeId(ws.getEmployee().getId())
                .employeeFullName(ws.getEmployee().getFullName())
                .employeeUsername(ws.getEmployee().getUsername())
                .shiftId(ws.getShift().getId())
                .shiftName(ws.getShift().getName())
                .workDate(ws.getWorkDate())
                .isPresent(ws.getIsPresent())
                .checkInTime(ws.getCheckInTime())
                .checkOutTime(ws.getCheckOutTime())
                .createdAt(ws.getCreatedAt())
                .build();
    }
    // Private helpers

    private WorkSchedule getScheduleOrThrow(Long id) {
        return workScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkSchedule not found with id: " + id));
    }

    private Employee getEmployeeOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    private Shift getShiftOrThrow(Long id) {
        return shiftRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shift not found with id: " + id));
    }

    private void validateDateRange(LocalDate from, LocalDate to) {
        if (from != null && to != null && to.isBefore(from)) {
            throw new IllegalArgumentException("End date must not be before start date");
        }
    }

    private void validateCheckTimes(Instant checkIn, Instant checkOut) {
        if (checkIn != null && checkOut != null && !checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out time must be after check-in time");
        }
    }

    // CRUD

    @Override
    @Transactional
    public WorkScheduleResponseDTO create(WorkScheduleRequestDTO request) {
        Employee employee = getEmployeeOrThrow(request.getEmployeeId());
        Shift    shift    = getShiftOrThrow(request.getShiftId());

        if (workScheduleRepository.existsByEmployeeIdAndShiftIdAndWorkDate(
                request.getEmployeeId(), request.getShiftId(), request.getWorkDate())) {
            throw new IllegalArgumentException(
                    "A schedule already exists for this employee, shift and date.");
        }

        WorkSchedule ws = new WorkSchedule();
        ws.setEmployee(employee);
        ws.setShift(shift);
        ws.setWorkDate(request.getWorkDate());
        ws.setIsPresent(request.getIsPresent() != null ? request.getIsPresent() : false);
        ws.setCreatedAt(Instant.now());

        return toDTO(workScheduleRepository.save(ws));
    }

    /**
     * Partial update — only non-null fields in the DTO are applied.
     */
    @Override
    @Transactional
    public WorkScheduleResponseDTO update(Long id, WorkScheduleUpdateDTO request) {
        WorkSchedule ws = getScheduleOrThrow(id);

        if (request.getEmployeeId() != null) {
            ws.setEmployee(getEmployeeOrThrow(request.getEmployeeId()));
        }

        if (request.getShiftId() != null) {
            ws.setShift(getShiftOrThrow(request.getShiftId()));
        }

        if (request.getWorkDate() != null) {
            ws.setWorkDate(request.getWorkDate());
        }

        // Duplicate guard — only if the key triple has changed
        if (request.getEmployeeId() != null || request.getShiftId() != null || request.getWorkDate() != null) {
            Long      effectiveEmployeeId = ws.getEmployee().getId();
            Long      effectiveShiftId    = ws.getShift().getId();
            LocalDate effectiveDate       = ws.getWorkDate();

            boolean duplicate = workScheduleRepository
                    .existsByEmployeeIdAndShiftIdAndWorkDate(effectiveEmployeeId, effectiveShiftId, effectiveDate);

            // Allow if the only matching record is the one we're updating
            if (duplicate && !workScheduleRepository.findByEmployeeIdAndWorkDate(effectiveEmployeeId, effectiveDate)
                    .map(existing -> existing.getId().equals(id))
                    .orElse(false)) {
                throw new IllegalArgumentException(
                        "A schedule already exists for this employee, shift and date.");
            }
        }

        if (request.getIsPresent() != null) {
            ws.setIsPresent(request.getIsPresent());
        }

        if (request.getCheckInTime() != null) {
            ws.setCheckInTime(request.getCheckInTime());
        }

        if (request.getCheckOutTime() != null) {
            validateCheckTimes(ws.getCheckInTime(), request.getCheckOutTime());
            ws.setCheckOutTime(request.getCheckOutTime());
        }

        return toDTO(workScheduleRepository.save(ws));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!workScheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("WorkSchedule not found with id: " + id);
        }
        workScheduleRepository.deleteById(id);
    }

    // Single-record lookups

    @Override
    public WorkScheduleResponseDTO findById(Long id) {
        return toDTO(getScheduleOrThrow(id));
    }

    @Override
    public WorkScheduleResponseDTO findByEmployeeAndDate(Long employeeId, LocalDate date) {
        return workScheduleRepository.findByEmployeeIdAndWorkDate(employeeId, date)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkSchedule not found for employee " + employeeId + " on " + date));
    }

    // List / search

    @Override
    public List<WorkScheduleResponseDTO> findAll() {
        return workScheduleRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Page<WorkScheduleResponseDTO> search(
            Long employeeId, Long shiftId,
            LocalDate from, LocalDate to,
            Boolean isPresent, Pageable pageable) {
        return workScheduleRepository
                .search(employeeId, shiftId, from, to, isPresent, pageable)
                .map(this::toDTO);
    }

    // By employee

    @Override
    public List<WorkScheduleResponseDTO> findByEmployee(Long employeeId) {
        return workScheduleRepository.findByEmployeeId(employeeId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<WorkScheduleResponseDTO> findByEmployeeAndDateRange(Long employeeId, LocalDate from, LocalDate to) {
        validateDateRange(from, to);
        return workScheduleRepository.findByEmployeeIdAndWorkDateBetween(employeeId, from, to)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<WorkScheduleResponseDTO> findByEmployeeAndAttendance(Long employeeId, Boolean isPresent) {
        return workScheduleRepository.findByEmployeeIdAndIsPresent(employeeId, isPresent)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // By shift

    @Override
    public List<WorkScheduleResponseDTO> findByShift(Long shiftId) {
        return workScheduleRepository.findByShiftId(shiftId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<WorkScheduleResponseDTO> findByShiftAndDate(Long shiftId, LocalDate date) {
        return workScheduleRepository.findByShiftIdAndWorkDate(shiftId, date)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // By date

    @Override
    public List<WorkScheduleResponseDTO> findByDate(LocalDate date) {
        return workScheduleRepository.findByWorkDate(date)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<WorkScheduleResponseDTO> findByDateRange(LocalDate from, LocalDate to) {
        validateDateRange(from, to);
        return workScheduleRepository.findByWorkDateBetween(from, to)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Attendance

    @Override
    public List<WorkScheduleResponseDTO> findByAttendance(Boolean isPresent) {
        return workScheduleRepository.findByIsPresent(isPresent)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<WorkScheduleResponseDTO> findByDateAndAttendance(LocalDate date, Boolean isPresent) {
        return workScheduleRepository.findByWorkDateAndIsPresent(date, isPresent)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WorkScheduleResponseDTO checkIn(Long id, Instant checkInTime) {
        WorkSchedule ws = getScheduleOrThrow(id);
        ws.setCheckInTime(checkInTime);
        ws.setIsPresent(true);
        return toDTO(workScheduleRepository.save(ws));
    }

    @Override
    @Transactional
    public WorkScheduleResponseDTO checkOut(Long id, Instant checkOutTime) {
        WorkSchedule ws = getScheduleOrThrow(id);
        validateCheckTimes(ws.getCheckInTime(), checkOutTime);
        ws.setCheckOutTime(checkOutTime);
        return toDTO(workScheduleRepository.save(ws));
    }

    @Override
    @Transactional
    public WorkScheduleResponseDTO markPresent(Long id) {
        WorkSchedule ws = getScheduleOrThrow(id);
        ws.setIsPresent(true);
        return toDTO(workScheduleRepository.save(ws));
    }

    @Override
    @Transactional
    public WorkScheduleResponseDTO markAbsent(Long id) {
        WorkSchedule ws = getScheduleOrThrow(id);
        ws.setIsPresent(false);
        return toDTO(workScheduleRepository.save(ws));
    }

    // Statistics

    @Override
    public long countByDate(LocalDate date) {
        return workScheduleRepository.countByWorkDate(date);
    }

    @Override
    public long countPresentByDate(LocalDate date) {
        return workScheduleRepository.countByWorkDateAndIsPresent(date, true);
    }

    @Override
    public long countAbsentByDate(LocalDate date) {
        return workScheduleRepository.countByWorkDateAndIsPresent(date, false);
    }

    @Override
    public long countScheduledDays(Long employeeId, LocalDate from, LocalDate to) {
        validateDateRange(from, to);
        return workScheduleRepository.countScheduledDays(employeeId, from, to);
    }

    @Override
    public long countAttendedDays(Long employeeId, LocalDate from, LocalDate to) {
        validateDateRange(from, to);
        return workScheduleRepository.countByEmployeeIdAndWorkDateBetweenAndIsPresent(employeeId, from, to, true);
    }

}
