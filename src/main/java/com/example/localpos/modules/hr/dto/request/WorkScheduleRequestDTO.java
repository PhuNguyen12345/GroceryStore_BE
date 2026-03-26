package com.example.localpos.modules.hr.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class WorkScheduleRequestDTO {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Shift ID is required")
    private Long shiftId;

    @NotNull(message = "Work date is required")
    private LocalDate workDate;

    private Boolean isPresent = false;

    private BigDecimal openingCash;
}
