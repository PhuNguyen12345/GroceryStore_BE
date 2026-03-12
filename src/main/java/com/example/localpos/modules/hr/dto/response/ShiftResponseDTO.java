package com.example.localpos.modules.hr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShiftResponseDTO {

    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private Boolean isActive;
}
