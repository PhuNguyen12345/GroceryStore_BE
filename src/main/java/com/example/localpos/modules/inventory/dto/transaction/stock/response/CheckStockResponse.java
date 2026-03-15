package com.example.localpos.modules.inventory.dto.transaction.stock.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CheckStockResponse {
    //For FE, if availableQuantity is enough then allow order
    private boolean isAllAvailable;
    private List<StockItemStatus> itemStatuses;
}
