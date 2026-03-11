package com.example.localpos.modules.inventory.dto.transaction.imports.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/*Phiếu nhập*/
public class ImportReceiptRequest {
    @NotNull(message = "ID của kho không được để trống")
    private Long warehouseId;

    @NotNull(message = "ID của nhà cung cấp không được để trống")
    private Long supplierId;

    @NotNull(message = "ID nhân viên yêu cầu nhập phiếu không được để trống")
    private Long employeeId;

    private String note;

    @NotEmpty(message = "Phiếu nhập phải có ít nhất 1 sản phẩm")
    @Valid
    private List<ImportItemRequest> items;
}
