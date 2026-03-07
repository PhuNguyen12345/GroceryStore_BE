package com.example.localpos.modules.crm.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.enums.DiscountType;
import com.example.localpos.modules.crm.dto.request.VoucherCreateRequest;
import com.example.localpos.modules.crm.dto.request.VoucherUpdateRequest;
import com.example.localpos.modules.crm.dto.response.VoucherResponse;
import com.example.localpos.modules.crm.entity.Voucher;
import com.example.localpos.modules.crm.mapper.VoucherCreateRequestMapper;
import com.example.localpos.modules.crm.mapper.VoucherResponseMapper;
import com.example.localpos.modules.crm.mapper.VoucherUpdateRequestMapper;
import com.example.localpos.modules.crm.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherCreateRequestMapper createMapper;
    private final VoucherUpdateRequestMapper updateMapper;
    private final VoucherResponseMapper responseMapper;

    @Override
    public VoucherResponse create(VoucherCreateRequest request) {

        if (voucherRepository.findByCode(request.getCode()).isPresent()) {
            throw new IllegalArgumentException("Code already exists");
        }

        Voucher voucher = createMapper.toEntity(request);

        if (voucher.getQuantityUsed() == null) {
            voucher.setQuantityUsed(0);
        }

        if (voucher.getIsActive() == null) {
            voucher.setIsActive(true);
        }

        Voucher saved = voucherRepository.save(voucher);

        return responseMapper.toDto(saved);
    }

    @Override
    public VoucherResponse update(Long id, VoucherUpdateRequest request) {

        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        if (request.getCode() != null) {
            voucherRepository.findByCode(request.getCode()).ifPresent(existing -> {
                if (!existing.getId().equals(voucher.getId())) {
                    throw new IllegalArgumentException("Code already exists");
                }
            });
            voucher.setCode(request.getCode());
        }

        if (request.getQuantityLimit() != null) {
            voucher.setQuantityLimit(request.getQuantityLimit());
        }

        if (request.getMinOrderValue() != null) {
            voucher.setMinOrderValue(request.getMinOrderValue());
        }

        if (request.getDescription() != null) {
            voucher.setDescription(request.getDescription());
        }

        if (request.getDiscountType() != null) {
            voucher.setDiscountType(request.getDiscountType());
        }

        if (request.getDiscountValue() != null) {
            voucher.setDiscountValue(request.getDiscountValue());
        }

        if (request.getStartDate() != null) {
            voucher.setStartDate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            voucher.setEndDate(request.getEndDate());
        }

        if (request.getIsActive() != null) {
            voucher.setIsActive(request.getIsActive());
        }

        Voucher saved = voucherRepository.save(voucher);

        return responseMapper.toDto(saved);
    }

    @Override
    public VoucherResponse getById(Long id) {

        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        return responseMapper.toDto(voucher);
    }

    @Override
    public VoucherResponse getByCode(String code) {

        Voucher voucher = voucherRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        return responseMapper.toDto(voucher);
    }

    @Override
    public PageResponse<VoucherResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher> voucherPage = voucherRepository.findAll(pageable);

        List<VoucherResponse> responses = voucherPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<VoucherResponse>builder()
                .content(responses)
                .page(voucherPage.getNumber())
                .size(voucherPage.getSize())
                .totalElements(voucherPage.getTotalElements())
                .totalPages(voucherPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<VoucherResponse> filterByIsActive(Boolean isActive, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher> voucherPage = voucherRepository.findAllByIsActive(isActive, pageable);

        List<VoucherResponse> responses = voucherPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<VoucherResponse>builder()
                .content(responses)
                .page(voucherPage.getNumber())
                .size(voucherPage.getSize())
                .totalElements(voucherPage.getTotalElements())
                .totalPages(voucherPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<VoucherResponse> filterByDiscountType(DiscountType discountType, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher> voucherPage = voucherRepository.findAllByDiscountType(discountType, pageable);

        List<VoucherResponse> responses = voucherPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<VoucherResponse>builder()
                .content(responses)
                .page(voucherPage.getNumber())
                .size(voucherPage.getSize())
                .totalElements(voucherPage.getTotalElements())
                .totalPages(voucherPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<VoucherResponse> searchByCode(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher> voucherPage =
                voucherRepository.findAllByCodeContainingIgnoreCase(keyword, pageable);

        List<VoucherResponse> responses = voucherPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<VoucherResponse>builder()
                .content(responses)
                .page(voucherPage.getNumber())
                .size(voucherPage.getSize())
                .totalElements(voucherPage.getTotalElements())
                .totalPages(voucherPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<VoucherResponse> searchByDescription(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Voucher> voucherPage =
                voucherRepository.findAllByDescriptionContainingIgnoreCase(keyword, pageable);

        List<VoucherResponse> responses = voucherPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<VoucherResponse>builder()
                .content(responses)
                .page(voucherPage.getNumber())
                .size(voucherPage.getSize())
                .totalElements(voucherPage.getTotalElements())
                .totalPages(voucherPage.getTotalPages())
                .build();
    }

    @Override
    public void delete(Long id) {

        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        if (Boolean.FALSE.equals(voucher.getIsActive())) return;

        voucher.setIsActive(false);

        voucherRepository.save(voucher);
    }

    @Override
    public void restore(Long id) {

        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        voucher.setIsActive(true);

        voucherRepository.save(voucher);
    }
}