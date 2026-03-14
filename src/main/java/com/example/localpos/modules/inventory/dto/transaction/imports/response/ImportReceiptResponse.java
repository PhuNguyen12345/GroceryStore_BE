package com.example.localpos.modules.inventory.dto.transaction.imports.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportReceiptResponse {
    private Long transactionId;
    private String message;

}
