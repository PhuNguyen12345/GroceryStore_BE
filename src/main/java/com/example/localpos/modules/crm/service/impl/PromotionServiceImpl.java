package com.example.localpos.modules.crm.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.crm.dto.request.PromotionRequest;
import com.example.localpos.modules.crm.dto.response.PromotionResponse;
import com.example.localpos.modules.crm.entity.Promotion;
import com.example.localpos.modules.crm.mapper.PromotionResponseMapper;
import com.example.localpos.modules.crm.repository.PromotionRepository;
import com.example.localpos.modules.crm.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionResponseMapper responseMapper;

    @Override
    public PromotionResponse savePromotion(PromotionRequest requestBody) {
        if (ObjectUtils.isEmpty(requestBody)) {
            throw new IllegalArgumentException("Dữ liệu yêu cầu đang trống");
        }

        Promotion promotion = null;
        if (!ObjectUtils.isEmpty(requestBody.getId())) {
            promotion = promotionRepository.findById(requestBody.getId())
                    .orElse(new Promotion());
        } else {
            promotion = new Promotion();
        }

        BeanUtils.copyProperties(requestBody, promotion, "id");

        if (ObjectUtils.isEmpty(promotion.getIsActive())) {
            promotion.setIsActive(true);
        }

        Promotion saved = promotionRepository.save(promotion);
        return responseMapper.toDto(saved);
    }

    @Override
    public PromotionResponse getById(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi"));

        return responseMapper.toDto(promotion);
    }

    @Override
    public PageResponse<PromotionResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Promotion> promotionPage = promotionRepository.findAll(pageable);

        List<PromotionResponse> responses = promotionPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<PromotionResponse>builder()
                .content(responses)
                .page(promotionPage.getNumber())
                .size(promotionPage.getSize())
                .totalElements(promotionPage.getTotalElements())
                .totalPages(promotionPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<PromotionResponse> filterByIsActive(Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Promotion> promotionPage = promotionRepository.findAllByIsActive(isActive, pageable);

        List<PromotionResponse> responses = promotionPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<PromotionResponse>builder()
                .content(responses)
                .page(promotionPage.getNumber())
                .size(promotionPage.getSize())
                .totalElements(promotionPage.getTotalElements())
                .totalPages(promotionPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<PromotionResponse> searchByName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Promotion> promotionPage = promotionRepository.findAllByNameContainingIgnoreCase(keyword, pageable);

        List<PromotionResponse> responses = promotionPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<PromotionResponse>builder()
                .content(responses)
                .page(promotionPage.getNumber())
                .size(promotionPage.getSize())
                .totalElements(promotionPage.getTotalElements())
                .totalPages(promotionPage.getTotalPages())
                .build();
    }

    @Override
    public void delete(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi"));

        if (Boolean.FALSE.equals(promotion.getIsActive())) {
            return;
        }

        promotion.setIsActive(false);
        promotionRepository.save(promotion);
    }

    @Override
    public void restore(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi"));

        promotion.setIsActive(true);
        promotionRepository.save(promotion);
    }
}