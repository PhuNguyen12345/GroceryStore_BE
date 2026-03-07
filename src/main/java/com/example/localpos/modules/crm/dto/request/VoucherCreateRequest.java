package com.example.localpos.modules.crm.dto.request;

import com.example.localpos.enums.DiscountType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class VoucherCreateRequest {

    @NotBlank(message = "Code is required")
    @Size(max = 20, message = "Code must be at most 20 characters")
    private String code;

    @Min(value = 1, message = "Quantity limit must be at least 1")
    private Integer quantityLimit;

    @DecimalMin(value = "0.00", message = "Min order value must be >= 0")
    private BigDecimal minOrderValue;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    @NotNull(message = "Discount type is required")
    private DiscountType discountType;

    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.01", message = "Discount value must be > 0")
    private BigDecimal discountValue;

    private Instant startDate;
    private Instant endDate;

    private Boolean isActive;
}