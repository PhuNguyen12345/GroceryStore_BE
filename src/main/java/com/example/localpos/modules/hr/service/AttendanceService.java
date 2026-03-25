package com.example.localpos.modules.hr.service;

import com.example.localpos.modules.hr.dto.response.AttendanceReportDTO;
import com.example.localpos.modules.hr.dto.response.MyScheduleResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    // ── Employee self-service ────────────────────────────────────────────────

    /**
     * All schedules assigned to the logged-in employee.
     */
    List<MyScheduleResponseDTO> getMySchedules(Long employeeId);

    /**
     * Schedules for today only.
     */
    MyScheduleResponseDTO getMyTodaySchedule(Long employeeId);

    /**
     * Schedules within a date range.
     */
    List<MyScheduleResponseDTO> getMySchedulesInRange(Long employeeId,
                                                      LocalDate from,
                                                      LocalDate to);

    /**
     * Clock in — uses the authenticated employee's ID so they
     * can only clock themselves in, not someone else.
     */
    MyScheduleResponseDTO clockIn(Long employeeId);

    /**
     * Clock out — same ownership enforcement.
     */
    MyScheduleResponseDTO clockOut(Long employeeId);

    // ── Manager reporting ────────────────────────────────────────────────────

    /**
     * Full attendance report for a specific date.
     */
    AttendanceReportDTO getDailyReport(LocalDate date);

    /**
     * Full attendance report for a date range.
     */
    List<AttendanceReportDTO> getRangeReport(LocalDate from, LocalDate to);

    /**
     * Attendance history for one employee — useful for the manager
     * to review an individual's punctuality over a period.
     */
    List<MyScheduleResponseDTO> getEmployeeAttendanceHistory(Long employeeId,
                                                             LocalDate from,
                                                             LocalDate to);
}
