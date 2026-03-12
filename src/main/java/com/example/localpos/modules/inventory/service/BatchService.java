package com.example.localpos.modules.inventory.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.batch.response.BatchResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface BatchService {
    PageResponse<BatchResponse> getAllBatches(int page, int size);
    PageResponse<BatchResponse> getBatches(String batchCode, String productName, String warehouseName,
                                           String supplierName, LocalDate fromExpiryDate,
                                           LocalDate toExpiryDate, int page, int size);
}
