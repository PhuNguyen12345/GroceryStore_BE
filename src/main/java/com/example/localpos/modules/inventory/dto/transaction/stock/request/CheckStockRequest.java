package com.example.localpos.modules.inventory.dto.transaction.stock.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CheckStockRequest {
    @NotNull(message = "Vui lòng truyền ID Kho cửa hàng")
    private Long warehouseId;

    @Valid
    @NotEmpty(message = "Giỏ hàng trống")
    private List<StockItemRequest> items;
}
