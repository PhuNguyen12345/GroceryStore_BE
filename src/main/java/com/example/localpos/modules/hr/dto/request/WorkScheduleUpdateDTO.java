package com.example.localpos.modules.hr.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
public class WorkScheduleUpdateDTO {

    private Long employeeId;

    private Long shiftId;

    private LocalDate workDate;

    private Boolean isPresent;

    private Instant checkInTime;

    private Instant checkOutTime;

    private BigDecimal openingCash;

    private BigDecimal closingCash;
}
