package com.example.localpos.modules.inventory.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {
    private String name;
    private String contactPerson;
    private String phone;
    private String address;
}
