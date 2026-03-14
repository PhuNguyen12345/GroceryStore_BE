package com.example.localpos.modules.inventory.dto.transaction.exports.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExportReceiptRequest {
    @NotNull(message = "Vui lòng chọn Kho xuất")
    private Long warehouseId;
    @NotNull(message = "Vui lòng truyền nhân viên lập phiếu")
    private Long employeeId;
    private String notes;
    @Valid
    @NotEmpty(message = "Phiếu xuất phải có ít nhất 1 mặt hàng")
    private List<ExportItemRequest> items;
}
