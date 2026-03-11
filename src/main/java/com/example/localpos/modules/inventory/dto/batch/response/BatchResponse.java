package com.example.localpos.modules.inventory.dto.batch.response;

import com.example.localpos.modules.product.entity.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchResponse {
    private Long id;
    private String productName;
    private String unitName;
    private String warehouseName;
    private String supplierName;
    private String batchCode;
    private LocalDate expiryDate;
    private Integer quantityAvailable;
    private BigDecimal importPrice;
    private Instant createdAt;
    private Integer discountPercent;
    private Boolean isDiscounted;
}
