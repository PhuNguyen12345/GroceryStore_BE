package com.example.localpos.modules.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUnitUpdateRequest {
    private Long productId;

    @Size(max = 50, message = "Tên đơn vị không được vượt quá 50 ký tự")
    private String unitName;

    @Min(value = 1, message = "Hệ số quy đổi phải lớn hơn 0")
    private Integer conversionFactor;

    @Size(max = 50, message = "Mã vạch không được vượt quá 50 ký tự")
    private String barcode;

    @DecimalMin(value = "0.0", inclusive = false, message = "Giá bán phải lớn hơn 0")
    private BigDecimal sellingPrice;

    @Min(value = 0, message = "Mức tồn kho tối thiểu phải lớn hơn hoặc bằng 0")
    private Integer reorderLevel;

    private Boolean isBaseUnit;

    private Boolean isActive;
}
