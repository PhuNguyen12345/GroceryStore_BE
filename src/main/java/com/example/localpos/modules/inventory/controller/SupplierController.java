package com.example.localpos.modules.inventory.controller;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.supplier.request.SupplierCreateRequest;
import com.example.localpos.modules.inventory.dto.supplier.request.SupplierUpdateRequest;
import com.example.localpos.modules.inventory.dto.supplier.response.SupplierResponse;
import com.example.localpos.modules.inventory.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.localpos.common.constants.ApiPaths;

@RestController
@RequestMapping(ApiPaths.InventoryCtrl.SUPPLIER)
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<SupplierResponse>> getAllSuppliers(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (name != null) {
            return ResponseEntity.ok(supplierService.findSuppliersByName(name, page, size));
        }
        return ResponseEntity.ok(supplierService.getAllSuppliers(page, size));
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@Valid @RequestBody SupplierCreateRequest supplierRequest) {
        return ResponseEntity.ok(supplierService.addSupplier(supplierRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> updateSupplier(
            @PathVariable Long id,
            @RequestBody SupplierUpdateRequest supplierRequest
    ) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Đã ngưng nhà cung cấp:  " + id);
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<SupplierResponse> restoreSupplier(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.restoreSupplier(id));
    }
}
