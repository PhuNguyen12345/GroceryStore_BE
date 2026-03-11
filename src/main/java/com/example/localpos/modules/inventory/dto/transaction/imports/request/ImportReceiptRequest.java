package com.example.localpos.modules.inventory.dto.transaction.imports;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/*Phiếu nhập*/
public class ImportReceiptRequest {
    @NotNull(message = "ID của kho không được để trống")
    private Long warehouseId;

    @NotNull(message = "ID nhà cung cấp không được để trống")
    private Long customerId;

    @NotNull(message = "ID nhân viên yêu cầu nhập phiếu không được để trống")
    private Long employeeId;

    @NotNull(message = "Giá nhập không được để trống")
    @Min(value = 0, message = "Giá nhập không hợp lệ")
    private BigDecimal importPrice;
    private LocalDate expiryDate;
}
