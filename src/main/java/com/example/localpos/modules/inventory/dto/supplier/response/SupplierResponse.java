package com.example.localpos.modules.inventory.dto.supplier.response;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.entity.Supplier;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResponse {
    private Long id;
    private String name;
    private String contactPerson;
    private String phone;
    private String address;
    private boolean isActive;
}
