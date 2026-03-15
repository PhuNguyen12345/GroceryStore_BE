package com.example.localpos.modules.inventory.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.modules.inventory.dto.transaction.stock.request.CheckStockRequest;
import com.example.localpos.modules.inventory.dto.transaction.stock.response.CheckStockResponse;
import com.example.localpos.modules.inventory.service.InventoryStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.InventoryCtrl.STOCK)
@RequiredArgsConstructor
public class InventoryStockController {
    private final InventoryStockService stockService;

    // POST /api/v1/inventory/stock/check
    @PostMapping("/check")
    public ResponseEntity<CheckStockResponse> checkStock(@Valid @RequestBody CheckStockRequest request) {
        return ResponseEntity.ok(stockService.checkCartStock(request));
    }

    @GetMapping("/{productUnitId}")
    public ResponseEntity<Integer> getAvailableStock(
            @PathVariable Long productUnitId,
            @RequestParam Long warehouseId) { // Truyền ID kho cửa hàng đang xem

        // Gọi lại đúng cái hàm tính tổng bằng lệnh COALESCE ở Repository
        Integer available = stockService.getAvailableStockByUnit(warehouseId, productUnitId);
        return ResponseEntity.ok(available);
    }
}
