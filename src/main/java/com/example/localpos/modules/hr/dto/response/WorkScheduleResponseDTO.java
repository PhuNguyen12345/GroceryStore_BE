package com.example.localpos.modules.hr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkScheduleResponseDTO {

    private Long id;

    // Employee summary
    private Long employeeId;
    private String employeeFullName;
    private String employeeUsername;

    // Shift summary
    private Long shiftId;
    private String shiftName;

    private LocalDate workDate;
    private Boolean isPresent;
    private Instant checkInTime;
    private Instant checkOutTime;
    private Instant createdAt;
}
