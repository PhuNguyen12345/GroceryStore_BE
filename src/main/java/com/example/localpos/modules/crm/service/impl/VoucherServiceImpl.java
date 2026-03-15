package com.example.localpos.modules.crm.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.enums.DiscountType;
import com.example.localpos.modules.crm.dto.request.VoucherRequest;
import com.example.localpos.modules.crm.dto.response.VoucherResponse;
import com.example.localpos.modules.crm.entity.Voucher;
import com.example.localpos.modules.crm.mapper.VoucherResponseMapper;
import com.example.localpos.modules.crm.repository.VoucherRepository;
import com.example.localpos.modules.crm.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherResponseMapper responseMapper;

    @Override
    public VoucherResponse saveVoucher(VoucherRequest requestBody) {
        // Validate request body.
        if (ObjectUtils.isEmpty(requestBody)) {
            throw new IllegalArgumentException("Dữ liệu gửi lên không được để trống");
        }

        // Ensure voucher date range is valid.
        if (requestBody.getStartDate() != null && requestBody.getEndDate() != null) {
            if (!requestBody.getStartDate().isBefore(requestBody.getEndDate())) {
                throw new IllegalArgumentException("Ngày bắt đầu phải nhỏ hơn ngày kết thúc");
            }
        }

        Voucher voucher;

        if (!ObjectUtils.isEmpty(requestBody.getId())) {
            // Update existing voucher.
            voucher = voucherRepository.findById(requestBody.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy voucher cần cập nhật"));

            // Reject code if it belongs to another voucher.
            voucherRepository.findByCode(requestBody.getCode())
                    .ifPresent(existingVoucher -> {
                        if (!existingVoucher.getId().equals(voucher.getId())) {
                            throw new IllegalArgumentException("Mã voucher đã tồn tại");
                        }
                    });
        } else {
            // Create new voucher with unique code.
            if (voucherRepository.findByCode(requestBody.getCode()).isPresent()) {
                throw new IllegalArgumentException("Mã voucher đã tồn tại");
            }
            voucher = new Voucher();
        }

        // Copy editable fields from request into entity.
        BeanUtils.copyProperties(requestBody, voucher, "id");

        // Default voucher status to active when missing.
        if (ObjectUtils.isEmpty(voucher.getIsActive())) {
            voucher.setIsActive(true);
        }

        Voucher saved = voucherRepository.save(voucher);
        return responseMapper.toDto(saved);
    }

    @Override
    public VoucherResponse getById(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mã voucher chưa tồn tại"));

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
        // Return all vouchers when no active filter is provided.
        Page<Voucher> voucherPage = (isActive == null)
                ? voucherRepository.findAll(pageable)
                : voucherRepository.findAllByIsActive(isActive, pageable);

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
        // Fallback to full list when search keyword is empty.
        Page<Voucher> voucherPage = (keyword == null || keyword.trim().isBlank())
                ? voucherRepository.findAll(pageable)
                : voucherRepository.findAllByCodeContainingIgnoreCase(keyword.trim(), pageable);

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
        // Fallback to full list when search keyword is empty.
        Page<Voucher> voucherPage = (keyword == null || keyword.trim().isBlank())
                ? voucherRepository.findAll(pageable)
                : voucherRepository.findAllByDescriptionContainingIgnoreCase(keyword.trim(), pageable);

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
    public PageResponse<VoucherResponse> getApplicableVouchers(BigDecimal orderValue, int page, int size) {
        // Validate order value for checkout voucher lookup.
        if (orderValue == null || orderValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Giá trị đơn hàng phải lớn hơn hoặc bằng 0");
        }

        // Sort by expiry first so checkout can surface urgent vouchers earlier.
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("endDate").ascending().and(Sort.by("discountValue").descending()));
        Page<Voucher> voucherPage = voucherRepository.findApplicableVouchers(orderValue, Instant.now(), pageable);

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
        // Soft delete by switching active status off.
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mã voucher chưa tồn tại"));

        if (Boolean.FALSE.equals(voucher.getIsActive())) {
            return;
        }

        voucher.setIsActive(false);
        voucherRepository.save(voucher);
    }

    @Override
    public void restore(Long id) {
        // Restore voucher by switching active status on.
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mã voucher chưa tồn tại"));

        voucher.setIsActive(true);
        voucherRepository.save(voucher);
    }
}
