package com.example.localpos.modules.auth.dto;

import com.example.localpos.enums.EmployeeRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    private String accessToken;

    @Builder.Default
    private String tokenType = "Bearer";

    private Long   employeeId;
    private String username;
    private String fullName;
    private EmployeeRole role;
}
