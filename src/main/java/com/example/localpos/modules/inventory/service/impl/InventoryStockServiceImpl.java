package com.example.localpos.modules.inventory.service.impl;

import com.example.localpos.modules.inventory.dto.transaction.stock.request.CheckStockRequest;
import com.example.localpos.modules.inventory.dto.transaction.stock.request.StockItemRequest;
import com.example.localpos.modules.inventory.dto.transaction.stock.response.CheckStockResponse;
import com.example.localpos.modules.inventory.dto.transaction.stock.response.StockItemStatus;
import com.example.localpos.modules.inventory.repository.InventoryBatchRepository;
import com.example.localpos.modules.inventory.service.InventoryStockService;
import com.example.localpos.modules.product.entity.ProductUnit;
import com.example.localpos.modules.product.repository.ProductUnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryStockServiceImpl implements InventoryStockService {
    private final InventoryBatchRepository inventoryBatchRepository;
    private final ProductUnitRepository productUnitRepository;

    @Override
    public CheckStockResponse checkCartStock(CheckStockRequest request) {
        List<StockItemStatus> stockItemStatuses = new ArrayList<>();
        boolean allAvailable = true;

        for (StockItemRequest item : request.getItems()) {
            // Lấy thông tin cơ bản của Unit để hiển thị tên (Có thể dùng Fetch Join để tối ưu hơn nếu cần)
            ProductUnit unit = productUnitRepository.findById(item.getProductUnitId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

            // Gọi hàm tính tổng tồn kho vừa viết
            int totalAvailable = inventoryBatchRepository.getTotalAvailableStock(
                    request.getWarehouseId(), item.getProductUnitId());

            boolean enough = totalAvailable >= item.getRequestedQuantity();
            if (!enough) {
                allAvailable = false; // Chỉ cần 1 món thiếu, đánh sập toàn bộ giỏ hàng
            }
            //build status
            stockItemStatuses.add(StockItemStatus.builder()
                    .productUnitId(unit.getId())
                    .productName(unit.getProduct().getName())
                    .unitName(unit.getUnitName())
                    .requestedQuantity(item.getRequestedQuantity())
                    .availableQuantity(totalAvailable)
                    .hasEnough(enough)
                    .build());
        }
        return CheckStockResponse.builder()
                .isAllAvailable(allAvailable)
                .itemStatuses(stockItemStatuses)
                .build();
    }

    @Override
    public Integer getAvailableStockByUnit(Long warehouseId, Long productUnitId) {
        return inventoryBatchRepository.getTotalAvailableStock(warehouseId, productUnitId);
    }
}
