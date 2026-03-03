package com.example.localpos.modules.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequest {
    @NotBlank
    private String name;
    private String contactPerson;
    private String phone;
    private String address;
}
