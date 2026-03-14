package com.example.localpos.modules.inventory.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.transaction.summary.details.TransactionDetailResponse;
import com.example.localpos.modules.inventory.dto.transaction.summary.details.TransactionLineItemResponse;
import com.example.localpos.modules.inventory.dto.transaction.summary.response.TransactionSummaryResponse;
import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.inventory.entity.InventoryTransaction;
import com.example.localpos.modules.inventory.mapper.inventory_transaction.TransactionSummaryResponseMapper;
import com.example.localpos.modules.inventory.repository.InventoryTransactionRepository;
import com.example.localpos.modules.inventory.repository.specification.TransactionSpecification;
import com.example.localpos.modules.inventory.service.InventoryTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

    private final InventoryTransactionRepository repository;
    private final TransactionSummaryResponseMapper mapper;
    @Override
    public PageResponse<TransactionSummaryResponse> getTransactions(String transactionType, String warehouseName,
                                                                    String employeeName, LocalDate fromDate,
                                                                    LocalDate toDate, Pageable pageable) {
        //Call specification
        Specification<InventoryTransaction> spec = TransactionSpecification.filterTransactions(transactionType,
                warehouseName, employeeName, fromDate, toDate);
        //Query to get Page from spec and pageRequest
        Page<InventoryTransaction> transactionPage = repository.findAll(spec, pageable);
        //get page content
        List<InventoryTransaction> contentList =  transactionPage.getContent();
        //convert to list of response
        List<TransactionSummaryResponse> responseList =  mapper.toDtoList(contentList);
        //build page response
        return PageResponse.<TransactionSummaryResponse> builder()
                .page(transactionPage.getNumber())
                .size(transactionPage.getSize())
                .content(responseList)
                .totalElements(transactionPage.getTotalElements())
                .totalPages(transactionPage.getTotalPages())
                .build();
    }

    @Override
    public TransactionDetailResponse getTransactionDetailById(Long transactionId) {
        // 1. Gọi Database (Sử dụng Specification Fetch Join để chống N+1)
        InventoryTransaction transaction = repository
                .findOne(TransactionSpecification.hasIdWithDetails(transactionId))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu giao dịch với ID: " + transactionId));

        // 2. Map cái Ruột (TransactionDetails -> LineItemResponse)
        List<TransactionLineItemResponse> lineItems = transaction.getTransactionDetails().stream()
                .map(detail -> {
                    InventoryBatch batch = detail.getInventoryBatch();
                    return TransactionLineItemResponse.builder()
                            .productName(batch.getProductUnit().getProduct().getName())
                            .unitName(batch.getProductUnit().getUnitName())
                            .batchCode(batch.getBatchCode())
                            .quantity(detail.getQuantity())
                            .price(batch.getImportPrice())
                            .build();
                })
                .toList();

        // 3. Map cái Vỏ (Sổ cái) và nhét cái Ruột vào
        return TransactionDetailResponse.builder()
                .id(transaction.getId())
                .transactionType(transaction.getTransactionType())
                .warehouseName(transaction.getWarehouse() != null ? transaction.getWarehouse().getName() : null)
                .employeeName(transaction.getEmployee() != null ? transaction.getEmployee().getUsername() : null)
                .createdAt(transaction.getCreatedAt())
                .note(transaction.getNote())
                .items(lineItems) // Nối danh sách mặt hàng vào đây
                .build();
    }
}
