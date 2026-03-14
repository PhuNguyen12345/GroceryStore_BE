package com.example.localpos.modules.inventory.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.transaction.summary.response.TransactionSummaryResponse;

import java.time.LocalDate;

public interface InventoryTransactionService {
    PageResponse<TransactionSummaryResponse> getTransactions(String transactionType, String warehouseName, String employeeName,
                                                             LocalDate fromDate, LocalDate toDatem, int page, int size);
}
