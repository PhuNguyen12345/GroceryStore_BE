package com.example.localpos.modules.hr.controller;

import com.example.localpos.modules.hr.dto.response.AttendanceReportDTO;
import com.example.localpos.modules.hr.dto.response.MyScheduleResponseDTO;
import com.example.localpos.modules.hr.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Manager/Admin only — attendance reporting and individual employee history.
 */
@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'STORE_MANAGER')")
public class AttendanceReportController {

    private final AttendanceService attendanceService;

    /**
     * GET /api/v1/attendance/report/daily?date=2024-06-01
     * <p>
     * Full attendance report for one day. Shows every scheduled employee with:
     * - PRESENT   : on time
     * - LATE      : clocked in after shift start (includes minutesLate)
     * - ABSENT    : never clocked in
     * - NOT_YET   : shift hasn't started yet
     */
    @GetMapping("/report/daily")
    public ResponseEntity<AttendanceReportDTO> getDailyReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // Defaults to today if no date provided
        return ResponseEntity.ok(
                attendanceService.getDailyReport(date != null ? date : LocalDate.now()));
    }

    /**
     * GET /api/v1/attendance/report/range?from=2024-06-01&to=2024-06-07
     * <p>
     * Returns one daily report per day in the range.
     * Useful for weekly/monthly review.
     */
    @GetMapping("/report/range")
    public ResponseEntity<List<AttendanceReportDTO>> getRangeReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(attendanceService.getRangeReport(from, to));
    }

    /**
     * GET /api/v1/attendance/employee/{employeeId}?from=&to=
     * <p>
     * Full attendance history for a specific employee.
     * Used to review individual punctuality or investigate absences.
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<MyScheduleResponseDTO>> getEmployeeHistory(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(
                attendanceService.getEmployeeAttendanceHistory(employeeId, from, to));
    }
}
