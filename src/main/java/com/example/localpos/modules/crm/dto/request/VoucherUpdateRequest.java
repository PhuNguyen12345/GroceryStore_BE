package com.example.localpos.modules.crm.dto.request;

import com.example.localpos.enums.DiscountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class VoucherUpdateRequest {

    @Size(max = 20, message = "Code must be at most 20 characters")
    private String code;

    @Min(value = 1, message = "Quantity limit must be at least 1")
    private Integer quantityLimit;

    @DecimalMin(value = "0.00", message = "Min order value must be >= 0")
    private BigDecimal minOrderValue;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    private DiscountType discountType;

    @DecimalMin(value = "0.01", message = "Discount value must be > 0")
    private BigDecimal discountValue;

    private Instant startDate;
    private Instant endDate;

    private Boolean isActive;
}