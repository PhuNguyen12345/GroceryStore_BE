package com.example.localpos.modules.hr.controller;

import com.example.localpos.modules.hr.dto.response.MyScheduleResponseDTO;
import com.example.localpos.modules.hr.service.AttendanceService;
import com.example.localpos.modules.hr.dto.response.EmployeeResponseDTO;
import com.example.localpos.modules.hr.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/me/schedule")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MyScheduleController {

    private final AttendanceService attendanceService;
        private final EmployeeService employeeService;

    /**
     * GET /api/v1/me/schedule
     * Returns all schedules assigned to the logged-in employee.
     */
    @GetMapping
    public ResponseEntity<List<MyScheduleResponseDTO>> getMySchedules(
                        Authentication authentication) {
        return ResponseEntity.ok(
                                attendanceService.getMySchedules(resolveCurrentEmployeeId(authentication)));
    }

    /**
     * GET /api/v1/me/schedule/today
     * Returns today's schedule for the logged-in employee.
     */
    @GetMapping("/today")
    public ResponseEntity<MyScheduleResponseDTO> getMyTodaySchedule(
                        Authentication authentication) {
        return ResponseEntity.ok(
                                attendanceService.getMyTodaySchedule(resolveCurrentEmployeeId(authentication)));
    }

    /**
     * GET /api/v1/me/schedule/range?from=2024-06-01&to=2024-06-30
     * Returns schedules within a date range.
     */
    @GetMapping("/range")
    public ResponseEntity<List<MyScheduleResponseDTO>> getMySchedulesInRange(
                        Authentication authentication,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(
                                attendanceService.getMySchedulesInRange(resolveCurrentEmployeeId(authentication), from, to));
    }

    /**
     * POST /api/v1/me/schedule/clock-in
     * Records check-in time for today's schedule.
     * Uses server time — no body required.
     */
    @PostMapping("/clock-in")
    public ResponseEntity<MyScheduleResponseDTO> clockIn(
                        Authentication authentication) {
        return ResponseEntity.ok(
                                attendanceService.clockIn(resolveCurrentEmployeeId(authentication)));
    }

    /**
     * POST /api/v1/me/schedule/clock-out
     * Records check-out time for today's schedule.
     * Uses server time — no body required.
     */
    @PostMapping("/clock-out")
    public ResponseEntity<MyScheduleResponseDTO> clockOut(
                        Authentication authentication) {
        return ResponseEntity.ok(
                                attendanceService.clockOut(resolveCurrentEmployeeId(authentication)));
    }

        private Long resolveCurrentEmployeeId(Authentication authentication) {
                EmployeeResponseDTO current = employeeService.findByUsername(authentication.getName());
                return current.getId();
        }
}
