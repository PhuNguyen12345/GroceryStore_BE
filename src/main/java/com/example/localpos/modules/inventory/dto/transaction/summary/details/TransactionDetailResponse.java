package com.example.localpos.modules.inventory.dto.transaction.summary.details;

import com.example.localpos.enums.InventoryTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailResponse {
    private Long id;
    private InventoryTransactionType transactionType;
    private String warehouseName;
    private String employeeName;
    private Instant createdAt;
    private String note;

    // ĐÂY LÀ ĐIỂM ĂN TIỀN: Danh sách các mặt hàng nằm gọn trong phiếu
    private List<TransactionLineItemResponse> items;
}
