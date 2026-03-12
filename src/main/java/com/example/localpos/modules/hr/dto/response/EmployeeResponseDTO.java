package com.example.localpos.modules.hr.dto.response;

import com.example.localpos.enums.EmployeeRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private EmployeeRole role;
    private Boolean isActive;
    private Instant createdAt;
}
