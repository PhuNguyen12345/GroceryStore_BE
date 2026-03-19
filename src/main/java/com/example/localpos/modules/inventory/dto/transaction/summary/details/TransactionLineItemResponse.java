package com.example.localpos.modules.inventory.dto.transaction.summary.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionLineItemResponse {
    // Chỉ lấy những thông tin thiết yếu nhất để người dùng đọc hiểu
    private String productName;  // Nối từ Batch -> ProductUnit -> Product
    private String unitName;     // Nối từ Batch -> ProductUnit
    private String batchCode;    // Mã lô hàng thực tế
    private Integer quantity;    // Số lượng nhập/xuất
    private BigDecimal price;    // Giá nhập (từ Batch)
}
