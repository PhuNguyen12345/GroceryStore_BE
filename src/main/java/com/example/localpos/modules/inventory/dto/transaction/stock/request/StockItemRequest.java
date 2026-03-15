package com.example.localpos.modules.inventory.dto.transaction.stock.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockItemRequest {
    @NotNull(message = "Chưa chọn sản phẩm")
    private Long productUnitId;

    @NotNull(message = "Số lượng không hợp lệ")
    private Integer requestedQuantity;
}