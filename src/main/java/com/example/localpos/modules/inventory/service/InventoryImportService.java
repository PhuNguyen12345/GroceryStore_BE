package com.example.localpos.modules.inventory.service;

import com.example.localpos.modules.inventory.dto.transaction.imports.request.ImportReceiptRequest;
import com.example.localpos.modules.inventory.dto.transaction.imports.response.ImportReceiptResponse;
import org.springframework.stereotype.Service;

@Service
public interface InventoryImportService {
    ImportReceiptResponse importGoods(ImportReceiptRequest request);
}
