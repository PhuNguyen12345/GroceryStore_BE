package com.example.localpos.modules.crm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {

    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "^(0(3|5|7|8|9)\\d{8}|(\\+84|84)(3|5|7|8|9)\\d{8})$",
            message = "Phone must be a valid Vietnam mobile number"
    )
    private String phone;

    @Size(max = 100, message = "Full name must be at most 100 characters")
    private String fullName;

    @Email(message = "Email is invalid")
    @Size(max = 100, message = "Email must be at most 100 characters")
    private String email;

    @Size(max = 500, message = "Address must be at most 500 characters")
    private String address;
}