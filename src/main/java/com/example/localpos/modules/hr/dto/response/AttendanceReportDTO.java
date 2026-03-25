package com.example.localpos.modules.hr.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceReportDTO {
    private LocalDate date;

    // Summary counts
    private long totalScheduled;
    private long totalPresent;
    private long totalLate;
    private long totalAbsent;
    private long totalNotYet;

    // Per-employee breakdown
    private List<AttendanceReportEntryDTO> entries;
}
