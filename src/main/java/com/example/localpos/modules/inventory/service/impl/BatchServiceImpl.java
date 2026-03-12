package com.example.localpos.modules.inventory.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.batch.response.BatchResponse;
import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.inventory.mapper.batch.BatchResponseMapper;
import com.example.localpos.modules.inventory.repository.InventoryBatchRepository;
import com.example.localpos.modules.inventory.service.BatchService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BatchServiceImpl implements BatchService {

    private InventoryBatchRepository batchRepository;
    private BatchResponseMapper responseMapper;

    @Override
    public PageResponse<BatchResponse> getAllBatches(int page, int size) {
        //Page of
        Pageable pageable = PageRequest.of(page, size);
        //use repo to find page by page and size
        Page<InventoryBatch> batchesPage = batchRepository.findAll(pageable);
        //get content
        List<InventoryBatch> batchesList = batchesPage.getContent();
        //map to list of dto
        List<BatchResponse> responseList = responseMapper.toDtoList(batchesList);
        //build response
        return PageResponse.<BatchResponse> builder()
                .page(batchesPage.getNumber())
                .size(batchesPage.getSize())
                .content(responseList)
                .totalPages(batchesPage.getTotalPages())
                .totalElements(batchesPage.getTotalElements())
                .build();
    }

    @Override
    public PageResponse<BatchResponse> getBatchesByName(int page, int size, String name) {
        return null;
    }
}
