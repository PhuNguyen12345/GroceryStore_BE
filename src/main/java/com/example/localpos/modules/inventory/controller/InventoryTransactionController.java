package com.example.localpos.modules.inventory.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.transaction.exports.request.ExportReceiptRequest;
import com.example.localpos.modules.inventory.dto.transaction.imports.request.ImportReceiptRequest;
import com.example.localpos.modules.inventory.dto.transaction.imports.response.ImportReceiptResponse;
import com.example.localpos.modules.inventory.dto.transaction.summary.details.TransactionDetailResponse;
import com.example.localpos.modules.inventory.dto.transaction.summary.response.TransactionSummaryResponse;
import com.example.localpos.modules.inventory.service.InventoryExportService;
import com.example.localpos.modules.inventory.service.InventoryImportService;
import com.example.localpos.modules.inventory.service.InventoryTransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(ApiPaths.InventoryCtrl.TRANSACTION)
@AllArgsConstructor
public class InventoryTransactionController {

    private final InventoryImportService  inventoryImportService;
    private final InventoryTransactionService inventoryTransactionService;
    private final InventoryExportService  inventoryExportService;

    @PostMapping("/import")
    public ResponseEntity<ImportReceiptResponse> importGoods(@Valid @RequestBody ImportReceiptRequest request){
        //call service
        ImportReceiptResponse response = inventoryImportService.importGoods(request);
        //return
        return ResponseEntity.ok(response);
    }

    @PostMapping("/export")
    public ResponseEntity<ImportReceiptResponse> exportGoods(@Valid @RequestBody ExportReceiptRequest request){
        //call service
        ImportReceiptResponse response = inventoryExportService.exportGoods(request);
        //return
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<TransactionSummaryResponse>> getTransactions(
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) String warehouseName,
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        PageResponse<TransactionSummaryResponse> response = inventoryTransactionService.getTransactions(
                transactionType, warehouseName, employeeName, fromDate, toDate, pageable);

        return ResponseEntity.ok(response);
    }

    // API 2: Lấy chi tiết 1 phiếu (Bao gồm danh sách mặt hàng)
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDetailResponse> getTransactionById(@PathVariable Long id) {
        TransactionDetailResponse response = inventoryTransactionService.getTransactionDetailById(id);
        return ResponseEntity.ok(response);
    }

}
