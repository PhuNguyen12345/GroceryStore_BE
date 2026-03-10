package com.example.localpos.modules.hr.controller;

import com.example.localpos.modules.hr.dto.request.WorkScheduleRequestDTO;
import com.example.localpos.modules.hr.dto.response.WorkScheduleResponseDTO;
import com.example.localpos.modules.hr.dto.request.WorkScheduleUpdateDTO;
import com.example.localpos.modules.hr.service.WorkScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/work-schedules")
@RequiredArgsConstructor
public class WorkScheduleController {

    private final WorkScheduleService workScheduleService;

    // CRUD

    /**
     * POST /api/v1/work-schedules
     */
    @PostMapping
    public ResponseEntity<WorkScheduleResponseDTO> create(
            @Valid @RequestBody WorkScheduleRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workScheduleService.create(request));
    }

    /**
     * PATCH /api/v1/work-schedules/{id}
     * Partially update — only supplied fields are modified.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<WorkScheduleResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody WorkScheduleUpdateDTO request) {
        return ResponseEntity.ok(workScheduleService.update(id, request));
    }

    /**
     * DELETE /api/v1/work-schedules/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Single-record lookups

    /**
     * GET /api/v1/work-schedules/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkScheduleResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(workScheduleService.findById(id));
    }

    /**
     * GET /api/v1/work-schedules/employee/{employeeId}/date/{date}
     */
    @GetMapping("/employee/{employeeId}/date/{date}")
    public ResponseEntity<WorkScheduleResponseDTO> findByEmployeeAndDate(
            @PathVariable Long employeeId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(workScheduleService.findByEmployeeAndDate(employeeId, date));
    }

    // List / search

    /**
     * GET /api/v1/work-schedules
     */
    @GetMapping
    public ResponseEntity<List<WorkScheduleResponseDTO>> findAll() {
        return ResponseEntity.ok(workScheduleService.findAll());
    }

    /**
     * GET /api/v1/work-schedules/search
     *   ?employeeId=&shiftId=&from=&to=&isPresent=&page=&size=&sort=
     */
    @GetMapping("/search")
    public ResponseEntity<Page<WorkScheduleResponseDTO>> search(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Long shiftId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) Boolean isPresent,
            @PageableDefault(size = 20, sort = "workDate") Pageable pageable) {
        return ResponseEntity.ok(
                workScheduleService.search(employeeId, shiftId, from, to, isPresent, pageable));
    }

    // By employee

    /**
     * GET /api/v1/work-schedules/employee/{employeeId}
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<WorkScheduleResponseDTO>> findByEmployee(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(workScheduleService.findByEmployee(employeeId));
    }

    /**
     * GET /api/v1/work-schedules/employee/{employeeId}/range?from=&to=
     */
    @GetMapping("/employee/{employeeId}/range")
    public ResponseEntity<List<WorkScheduleResponseDTO>> findByEmployeeAndDateRange(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(workScheduleService.findByEmployeeAndDateRange(employeeId, from, to));
    }

    /**
     * GET /api/v1/work-schedules/employee/{employeeId}/attendance?isPresent=
     */
    @GetMapping("/employee/{employeeId}/attendance")
    public ResponseEntity<List<WorkScheduleResponseDTO>> findByEmployeeAndAttendance(
            @PathVariable Long employeeId,
            @RequestParam Boolean isPresent) {
        return ResponseEntity.ok(workScheduleService.findByEmployeeAndAttendance(employeeId, isPresent));
    }

    // By shift

    /**
     * GET /api/v1/work-schedules/shift/{shiftId}
     */
    @GetMapping("/shift/{shiftId}")
    public ResponseEntity<List<WorkScheduleResponseDTO>> findByShift(
            @PathVariable Long shiftId) {
        return ResponseEntity.ok(workScheduleService.findByShift(shiftId));
    }

    /**
     * GET /api/v1/work-schedules/shift/{shiftId}/date/{date}
     */
    @GetMapping("/shift/{shiftId}/date/{date}")
    public ResponseEntity<List<WorkScheduleResponseDTO>> findByShiftAndDate(
            @PathVariable Long shiftId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(workScheduleService.findByShiftAndDate(shiftId, date));
    }

    // By date

    /**
     * GET /api/v1/work-schedules/date/{date}
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<List<WorkScheduleResponseDTO>> findByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(workScheduleService.findByDate(date));
    }

    /**
     * GET /api/v1/work-schedules/date/range?from=&to=
     */
    @GetMapping("/date/range")
    public ResponseEntity<List<WorkScheduleResponseDTO>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(workScheduleService.findByDateRange(from, to));
    }

    // Attendance

    /**
     * GET /api/v1/work-schedules/attendance?isPresent=
     */
    @GetMapping("/attendance")
    public ResponseEntity<List<WorkScheduleResponseDTO>> findByAttendance(
            @RequestParam Boolean isPresent) {
        return ResponseEntity.ok(workScheduleService.findByAttendance(isPresent));
    }

    /**
     * GET /api/v1/work-schedules/date/{date}/attendance?isPresent=
     */
    @GetMapping("/date/{date}/attendance")
    public ResponseEntity<List<WorkScheduleResponseDTO>> findByDateAndAttendance(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Boolean isPresent) {
        return ResponseEntity.ok(workScheduleService.findByDateAndAttendance(date, isPresent));
    }

    /**
     * PATCH /api/v1/work-schedules/{id}/check-in
     * Body: { "checkInTime": "2024-06-01T08:00:00Z" }
     */
    @PatchMapping("/{id}/check-in")
    public ResponseEntity<WorkScheduleResponseDTO> checkIn(
            @PathVariable Long id,
            @RequestBody Map<String, Instant> body) {
        Instant checkInTime = body.get("checkInTime");
        if (checkInTime == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(workScheduleService.checkIn(id, checkInTime));
    }

    /**
     * PATCH /api/v1/work-schedules/{id}/check-out
     * Body: { "checkOutTime": "2024-06-01T17:00:00Z" }
     */
    @PatchMapping("/{id}/check-out")
    public ResponseEntity<WorkScheduleResponseDTO> checkOut(
            @PathVariable Long id,
            @RequestBody Map<String, Instant> body) {
        Instant checkOutTime = body.get("checkOutTime");
        if (checkOutTime == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(workScheduleService.checkOut(id, checkOutTime));
    }

    /**
     * PATCH /api/v1/work-schedules/{id}/mark-present
     */
    @PatchMapping("/{id}/mark-present")
    public ResponseEntity<WorkScheduleResponseDTO> markPresent(@PathVariable Long id) {
        return ResponseEntity.ok(workScheduleService.markPresent(id));
    }

    /**
     * PATCH /api/v1/work-schedules/{id}/mark-absent
     */
    @PatchMapping("/{id}/mark-absent")
    public ResponseEntity<WorkScheduleResponseDTO> markAbsent(@PathVariable Long id) {
        return ResponseEntity.ok(workScheduleService.markAbsent(id));
    }

    // Statistics

    /**
     * GET /api/v1/work-schedules/stats/daily?date=
     * Returns scheduled / present / absent counts for a given date.
     */
    @GetMapping("/stats/daily")
    public ResponseEntity<Map<String, Object>> dailyStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(Map.of(
                "date",      date,
                "scheduled", workScheduleService.countByDate(date),
                "present",   workScheduleService.countPresentByDate(date),
                "absent",    workScheduleService.countAbsentByDate(date)
        ));
    }

    /**
     * GET /api/v1/work-schedules/stats/employee/{employeeId}?from=&to=
     * Returns scheduled vs attended days for an employee over a date range.
     */
    @GetMapping("/stats/employee/{employeeId}")
    public ResponseEntity<Map<String, Object>> employeeStats(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        long scheduled = workScheduleService.countScheduledDays(employeeId, from, to);
        long attended  = workScheduleService.countAttendedDays(employeeId, from, to);
        return ResponseEntity.ok(Map.of(
                "employeeId",    employeeId,
                "from",          from,
                "to",            to,
                "scheduledDays", scheduled,
                "attendedDays",  attended,
                "absentDays",    scheduled - attended
        ));
    }
}
