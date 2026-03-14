package com.example.localpos.modules.inventory.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.transaction.summary.response.TransactionSummaryResponse;
import com.example.localpos.modules.inventory.entity.InventoryTransaction;
import com.example.localpos.modules.inventory.repository.InventoryTransactionRepository;
import com.example.localpos.modules.inventory.repository.specification.TransactionSpecification;
import com.example.localpos.modules.inventory.service.InventoryTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

    private final InventoryTransactionRepository  repository;

    @Override
    public PageResponse<TransactionSummaryResponse> getTransactions(String transactionType, String warehouseName,
                                                                    String employeeName, LocalDate fromDate,
                                                                    LocalDate toDate, int page, int size) {
        //New Page
        Pageable pageRequest = PageRequest.of(page, size);
        //Call specification
        Specification<InventoryTransaction> spec = TransactionSpecification.filterTransactions(transactionType,
                warehouseName, employeeName, fromDate, toDate);
        //Query to get Page from spec and pageRequest
        Page<InventoryTransaction> transactionPage = repository.findAll(spec, pageRequest);
        //get page content
        List<InventoryTransaction> contentList =  transactionPage.getContent();
        return null;
    }
}
