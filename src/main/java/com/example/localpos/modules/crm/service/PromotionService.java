package com.example.localpos.modules.crm.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.crm.dto.request.PromotionRequest;
import com.example.localpos.modules.crm.dto.response.PromotionResponse;

public interface PromotionService {
    PromotionResponse savePromotion(PromotionRequest requestBody);

    PromotionResponse getById(Long id);

    PageResponse<PromotionResponse> getAll(int page, int size);

    PageResponse<PromotionResponse> filterByIsActive(Boolean isActive, int page, int size);

    PageResponse<PromotionResponse> searchByName(String keyword, int page, int size);

    void delete(Long id);

    void restore(Long id);
}
