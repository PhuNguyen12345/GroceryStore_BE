package com.example.localpos.modules.inventory.service;

import com.example.localpos.modules.inventory.dto.transaction.exports.request.ExportReceiptRequest;
import com.example.localpos.modules.inventory.dto.transaction.imports.response.ImportReceiptResponse;

public interface InventoryExportService {
    ImportReceiptResponse exportGoods(ExportReceiptRequest request);
}
