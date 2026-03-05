package com.example.localpos.modules.hr.dto.request;

import lombok.Getter;
import lombok.Setter;

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
}
