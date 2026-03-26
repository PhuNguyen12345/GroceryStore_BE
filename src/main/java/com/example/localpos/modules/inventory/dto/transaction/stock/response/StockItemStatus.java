package com.example.localpos.modules.inventory.dto.transaction.stock.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockItemStatus {
    private Long productUnitId;
    private String productName;
    private String unitName;
    private Integer requestedQuantity;
    private Integer availableQuantity;
    private boolean hasEnough; //enough quantity?
}