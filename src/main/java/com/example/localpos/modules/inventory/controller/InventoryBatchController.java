package com.example.localpos.modules.inventory.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.batch.response.BatchResponse;
import com.example.localpos.modules.inventory.service.BatchService;
import com.example.localpos.modules.inventory.service.InventoryImportService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(ApiPaths.InventoryCtrl.BATCH)
@AllArgsConstructor
public class InventoryBatchController {
    private final BatchService batchService;
    @GetMapping
    public ResponseEntity<PageResponse<BatchResponse>> getAllBatches(
            @RequestParam(required = false) String batchCode,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String warehouseName,
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fromExpiryDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate toExpiryDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        PageResponse<BatchResponse> response = batchService.getBatches(batchCode,productName,warehouseName,supplierName,
                fromExpiryDate,toExpiryDate, page, size);
        return ResponseEntity.ok(response);
    }
}
