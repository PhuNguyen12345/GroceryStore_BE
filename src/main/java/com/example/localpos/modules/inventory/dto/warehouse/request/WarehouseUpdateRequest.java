package com.example.localpos.modules.inventory.dto.warehouse.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WarehouseUpdateRequest {
    @Size(max = 150, message = "Tên kho không được vượt quá 150 kí tự.")
    private String name;
    @Size(max = 150, message = "Địa chỉ của kho không được vượt quá 150 ký tự")
    private String address;
    private Boolean isActive = true;
}
