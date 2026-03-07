package com.example.localpos.modules.crm.dto.response;

import com.example.localpos.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class VoucherResponse {
    private Long id;
    private String code;
    private Integer quantityLimit;
    private Integer quantityUsed;
    private BigDecimal minOrderValue;
    private String description;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private Instant startDate;
    private Instant endDate;
    private Boolean isActive;
}