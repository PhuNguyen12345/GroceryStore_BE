package com.example.localpos.modules.product.dto.response;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUnitResponse {

    private Long id;

    private Long productId;

    private String productName;

    private String unitName;

    private Integer conversionFactor;

    private String barcode;

    private BigDecimal sellingPrice;

    private Integer reorderLevel;

    private Boolean isBaseUnit;

    private Boolean isActive;
}
