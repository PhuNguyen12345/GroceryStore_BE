package com.example.localpos.modules.hr.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ShiftUpdateDTO {

    @Size(max = 50, message = "Shift name must not exceed 50 characters")
    private String name;

    private LocalTime startTime;

    private LocalTime endTime;

    private String description;

    private Boolean isActive;
}
