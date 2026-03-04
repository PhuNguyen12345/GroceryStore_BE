package com.example.localpos.modules.crm.dto.request;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindCustomerByPhoneRequest {
    @NotBlank(message = "Phone is required")
    @Size(max = 15, message = "Phone must be <= 15 characters")
    @Pattern(regexp = "^[0-9+]{8,15}$", message = "Phone format is invalid")
    private String phone;
    
}
