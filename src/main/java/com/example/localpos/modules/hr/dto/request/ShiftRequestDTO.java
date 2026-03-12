package com.example.localpos.modules.hr.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ShiftRequestDTO {

    @NotBlank(message = "Shift name is required")
    @Size(max = 50, message = "Shift name must not exceed 50 characters")
    private String name;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    private String description;

    private Boolean isActive = true;
}
