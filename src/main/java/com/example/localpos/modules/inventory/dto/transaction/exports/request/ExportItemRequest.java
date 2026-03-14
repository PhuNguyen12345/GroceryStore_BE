package com.example.localpos.modules.inventory.dto.transaction.exports.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExportItemRequest {
    @NotNull(message = "Chưa chọn đơn vị sản phẩm")
    private Long productUnitId;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng xuất phải lớn hơn 0")
    private Integer quantity;
}
