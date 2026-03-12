package com.example.localpos.modules.inventory.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.warehouse.request.WarehouseCreateRequest;
import com.example.localpos.modules.inventory.dto.warehouse.request.WarehouseUpdateRequest;
import com.example.localpos.modules.inventory.dto.warehouse.response.WarehouseResponse;
import org.springframework.data.domain.Pageable;

public interface WarehouseService {
    public PageResponse<WarehouseResponse> getAllWarehouses(int page, int size);
    public WarehouseResponse addWarehouse(WarehouseCreateRequest warehouseCreateRequest);
    public WarehouseResponse updateWarehouse(WarehouseUpdateRequest warehouseCreateRequest, Long id);
    public PageResponse<WarehouseResponse> findWarehousesByName(String name, int page, int size);
    void deleteWarehouse(Long id);
}
