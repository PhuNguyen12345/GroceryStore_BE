package com.example.localpos.modules.inventory.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.transaction.summary.details.TransactionDetailResponse;
import com.example.localpos.modules.inventory.dto.transaction.summary.response.TransactionSummaryResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface InventoryTransactionService {
    PageResponse<TransactionSummaryResponse> getTransactions(String transactionType, String warehouseName, String employeeName,
                                                             LocalDate fromDate, LocalDate toDatem, Pageable pageable);

    TransactionDetailResponse getTransactionDetailById(Long transactionId);
}
