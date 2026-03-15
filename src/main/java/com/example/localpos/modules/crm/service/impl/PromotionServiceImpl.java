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
        // Validate request body.
        if (request == null) {
            throw new RuntimeException("Dữ liệu khuyến mãi không được để trống");
        }

        // Ensure promotion date range is valid.
        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (!request.getStartDate().isBefore(request.getEndDate())) {
                throw new IllegalArgumentException("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
            }
        }

        Promotion promotion;

        if (request.getId() != null) {
            // Update existing promotion.
            promotion = promotionRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi"));

            if (request.getName() != null && !request.getName().trim().isBlank()) {
                String newName = request.getName().trim();

                // Reject name if it belongs to another promotion.
                if (!newName.equalsIgnoreCase(promotion.getName())
                        && promotionRepository.existsByNameIgnoreCaseAndIdNot(newName, promotion.getId())) {
                    throw new RuntimeException("Tên khuyến mãi đã tồn tại");
                }

                promotion.setName(newName);
            }

        } else {
            // Create new promotion with unique name.
            if (request.getName() == null || request.getName().trim().isBlank()) {
                throw new RuntimeException("Tên khuyến mãi không được để trống");
            }

            String newName = request.getName().trim();

            if (promotionRepository.existsByNameIgnoreCase(newName)) {
                throw new RuntimeException("Tên khuyến mãi đã tồn tại");
            }

            promotion = new Promotion();
            // Copy request fields except values handled manually below.
            BeanUtils.copyProperties(request, promotion, "id", "name", "bannerUrl");
            promotion.setName(newName);
            promotion.setBannerUrl(request.getBannerUrl());

            // Default promotion status to active when missing.
            if (promotion.getIsActive() == null) {
                promotion.setIsActive(true);
            }

            Promotion savedPromotion = promotionRepository.save(promotion);
            return responseMapper.toDto(savedPromotion);
        }

        if (request.getDescription() != null) {
            promotion.setDescription(request.getDescription());
        }

        if (request.getBannerUrl() != null && !request.getBannerUrl().trim().isBlank()) {
            promotion.setBannerUrl(request.getBannerUrl().trim());
        }

        if (request.getStartDate() != null) {
            promotion.setStartDate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            promotion.setEndDate(request.getEndDate());
        }

        if (request.getIsActive() != null) {
            promotion.setIsActive(request.getIsActive());
        }

        Promotion savedPromotion = promotionRepository.save(promotion);
        return responseMapper.toDto(savedPromotion);
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
