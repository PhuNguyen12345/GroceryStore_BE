package com.example.localpos.modules.inventory.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.modules.inventory.dto.transaction.imports.request.ImportReceiptRequest;
import com.example.localpos.modules.inventory.dto.transaction.imports.response.ImportReceiptResponse;
import com.example.localpos.modules.inventory.service.InventoryImportService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.InventoryCtrl.TRANSACTION)
@AllArgsConstructor
public class InventoryTransactionController {

    private final InventoryImportService  inventoryImportService;

    @PostMapping("/import")
    public ResponseEntity<ImportReceiptResponse> importGoods(@Valid @RequestBody ImportReceiptRequest request){
        //call service
        ImportReceiptResponse response = inventoryImportService.importGoods(request);
        //return
        return ResponseEntity.ok(response);
    }
}
