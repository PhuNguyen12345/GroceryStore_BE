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

    @NotBlank(message = "Tên đơn vị không được để trống")
    @Size(max = 50, message = "Tên đơn vị không được vượt quá 50 ký tự")
    private String unitName;

    @Min(value = 1, message = "Hệ số quy đổi phải lớn hơn 0")
    private Integer conversionFactor;

    @Size(max = 50, message = "Mã vạch không được vượt quá 50 ký tự")
    private String barcode;

    @NotNull(message = "Giá bán không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá bán phải lớn hơn 0")
    private BigDecimal sellingPrice;

    @Min(value = 0, message = "Mức tồn kho tối thiểu phải lớn hơn hoặc bằng 0")
    private Integer reorderLevel;

    private Boolean isBaseUnit;

    private Boolean isActive;

}
