package com.example.localpos.modules.inventory.dto.transaction.summary.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionLineItemResponse {
    private String productName;
    private String unitName;
    private String batchCode;
    private Integer quantity;
    private BigDecimal price;
}
