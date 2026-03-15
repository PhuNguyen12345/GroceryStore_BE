package com.example.localpos.modules.inventory.dto.transaction.imports.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportItemRequest {
    @NotNull(message = "Đơn vị tính xét theo sản phẩm không được để trống")
    private Long productUnitId;

    @NotNull(message = "Số lượng nhập không được để trống")
    @Min(value = 1, message = "Số lượng nhập phải lớn hơn 0")
    private Integer quantity;

    @NotNull(message = "Giá nhập không được để trống")
    @Min(value = 0, message = "Giá nhập không hợp lệ")
    private BigDecimal importPrice;
    private LocalDate expiryDate;
}
