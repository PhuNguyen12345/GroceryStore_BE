package com.example.localpos.modules.inventory.dto.warehouse.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseResponse {
    private Long id;
    private String name;
    private String address;
    private Boolean isActive;
}
