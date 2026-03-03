package com.example.localpos.modules.inventory.dto.response;

import com.example.localpos.common.PageResponse;
import com.example.localpos.modules.inventory.entity.Supplier;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {
    private Long id;
    private String name;
    private String contactPerson;
    private String phone;
    private String address;
}
