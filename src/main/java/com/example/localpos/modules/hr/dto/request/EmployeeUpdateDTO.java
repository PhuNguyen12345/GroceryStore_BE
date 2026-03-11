package com.example.localpos.modules.hr.dto.request;

import com.example.localpos.enums.EmployeeRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUpdateDTO {

    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    // If provided, the password will be re-hashed and updated
    private String password;

    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 15, message = "Phone must not exceed 15 characters")
    private String phone;

    private EmployeeRole role;

    private Boolean isActive;
}
