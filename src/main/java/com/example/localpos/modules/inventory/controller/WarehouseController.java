package com.example.localpos.modules.inventory.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.warehouse.request.WarehouseCreateRequest;
import com.example.localpos.modules.inventory.dto.warehouse.request.WarehouseUpdateRequest;
import com.example.localpos.modules.inventory.dto.warehouse.response.WarehouseResponse;
import com.example.localpos.modules.inventory.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.InventoryCtrl.WAREHOUSE)
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<WarehouseResponse>> getAllWarehouses(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if  (name != null) {
            return ResponseEntity.ok(warehouseService.findWarehousesByName(name,page,size));
        }
        return ResponseEntity.ok(warehouseService.getAllWarehouses(page,size));
    }

    @PostMapping
    public ResponseEntity<WarehouseResponse> addWarehouse(
        @Valid @RequestBody WarehouseCreateRequest createRequest) {
        return ResponseEntity.ok(warehouseService.addWarehouse(createRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponse> updateWarehouse(
            @PathVariable Long id, @Valid @RequestBody WarehouseUpdateRequest updateRequest) {
        return ResponseEntity.ok(warehouseService.updateWarehouse(updateRequest,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok("Đã ngừng kho với id: " + id);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<WarehouseResponse> restoreWarehouse(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.restoreWarehouse(id));
    }
}
