package com.example.localpos.modules.hr.dto.response;

import com.example.localpos.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceReportEntryDTO {

    private Long scheduleId;
    private LocalDate workDate;

    // Employee info
    private Long employeeId;
    private String employeeFullName;
    private String employeeUsername;

    // Shift info
    private String shiftName;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;

    // Attendance detail
    private AttendanceStatus status;
    private Instant checkInTime;
    private Instant checkOutTime;

    // Only populated when status == LATE
    private Long minutesLate;
}
