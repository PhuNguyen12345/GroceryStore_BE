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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyScheduleResponseDTO {
    private Long scheduleId;
    private LocalDate workDate;

    // Shift info
    private Long shiftId;
    private String shiftName;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;

    // Attendance
    private Boolean isPresent;
    private Instant checkInTime;
    private Instant checkOutTime;
    private AttendanceStatus status;
}
