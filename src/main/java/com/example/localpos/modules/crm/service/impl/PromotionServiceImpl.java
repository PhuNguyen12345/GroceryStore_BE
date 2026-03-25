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

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionResponseMapper responseMapper;

    @Override
    public PromotionResponse savePromotion(PromotionRequest request) {
        if (request.getStartDate() != null
                && request.getEndDate() != null
                && !request.getStartDate().isBefore(request.getEndDate())) {
            throw new IllegalArgumentException("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
        }

        Promotion promotion;
        boolean isUpdate = request.getId() != null;

        if (isUpdate) {
            promotion = promotionRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi"));
        } else {
            promotion = new Promotion();
        }

        String name = request.getName().trim();

        if (isUpdate) {
            if (promotionRepository.existsByNameIgnoreCaseAndIdNot(name, promotion.getId())) {
                throw new RuntimeException("Tên khuyến mãi đã tồn tại");
            }
        } else {
            if (promotionRepository.existsByNameIgnoreCase(name)) {
                throw new RuntimeException("Tên khuyến mãi đã tồn tại");
            }
        }

        promotion.setName(name);
        promotion.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        promotion.setBannerUrl(request.getBannerUrl() != null ? request.getBannerUrl().trim() : null);
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());

        if (request.getIsActive() != null) {
            promotion.setIsActive(request.getIsActive());
        } else if (promotion.getIsActive() == null) {
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
        // Return all promotions when no active filter is provided.
        Page<Promotion> promotionPage = (isActive == null)
                ? promotionRepository.findAll(pageable)
                : promotionRepository.findAllByIsActive(isActive, pageable);

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
        // Fallback to full list when search keyword is empty.
        Page<Promotion> promotionPage = (keyword == null || keyword.trim().isBlank())
                ? promotionRepository.findAll(pageable)
                : promotionRepository.findAllByNameContainingIgnoreCase(keyword.trim(), pageable);

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
        // Soft delete by switching active status off.
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
        // Restore promotion by switching active status on.
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi"));

        promotion.setIsActive(true);
        promotionRepository.save(promotion);
    }
}
