package com.example.localpos.modules.inventory.dto.transaction.summary.response;

import com.example.localpos.enums.InventoryTransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionSummaryResponse {
    private Long id;
    private InventoryTransactionType transactionType;
    private String warehouseName;
    private String employeeName;
    private String note;
    private Instant createdAt;
}
