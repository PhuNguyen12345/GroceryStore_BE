package com.example.localpos.modules.inventory.controller;

import com.example.localpos.common.PageResponse;
import com.example.localpos.modules.inventory.dto.response.SupplierResponse;
import com.example.localpos.modules.inventory.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/")
    public ResponseEntity<PageResponse<SupplierResponse>> getAllSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<SupplierResponse> response = supplierService.getAllSuppliers(page, size);
        return ResponseEntity.ok(response);
    }
}
