package com.example.localpos.modules.pos.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class OrderDetailResponse {
    private Long id;
    private Long productUnitId;
    private String productUnitName;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
