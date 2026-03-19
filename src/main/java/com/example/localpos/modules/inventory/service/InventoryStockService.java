package com.example.localpos.modules.inventory.service;

import com.example.localpos.modules.inventory.dto.transaction.stock.request.CheckStockRequest;
import com.example.localpos.modules.inventory.dto.transaction.stock.response.CheckStockResponse;

public interface InventoryStockService {
    CheckStockResponse checkCartStock(CheckStockRequest request);
    Integer getAvailableStockByUnit(Long warehouseId, Long productUnitId);
}
