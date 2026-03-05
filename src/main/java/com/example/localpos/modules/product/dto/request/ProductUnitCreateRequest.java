package com.example.localpos.modules.product.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUnitCreateRequest {
    private Long productId;

    @NotBlank(message = "Unit name must not be blank")
    @Size(max = 50, message = "Unit name must not exceed 50 characters")
    private String unitName;

    @Min(value = 1, message = "Conversion factor must be greater than 0")
    private Integer conversionFactor;

    @Size(max = 50, message = "Barcode must not exceed 50 characters")
    private String barcode;

    @NotNull(message = "Selling price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Selling price must be greater than 0")
    private BigDecimal sellingPrice;

    @Min(value = 0, message = "Reorder level must be >= 0")
    private Integer reorderLevel;

    private Boolean isBaseUnit;

    private Boolean isActive;

}
