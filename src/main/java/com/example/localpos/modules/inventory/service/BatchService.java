package com.example.localpos.modules.inventory.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.batch.response.BatchResponse;
import org.springframework.data.domain.Page;

public interface BatchService {
    PageResponse<BatchResponse> getAllBatches(int page, int size);
    PageResponse<BatchResponse> getBatchesByName(int page, int size, String name);
}
