package com.example.localpos.modules.crm.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.enums.DiscountType;
import com.example.localpos.modules.crm.dto.request.VoucherRequest;
import com.example.localpos.modules.crm.dto.response.VoucherResponse;

public interface VoucherService {

    VoucherResponse saveVoucher(VoucherRequest request);

    VoucherResponse getById(Long id);

    VoucherResponse getByCode(String code);

    PageResponse<VoucherResponse> getAll(int page, int size);

    PageResponse<VoucherResponse> filterByIsActive(Boolean isActive, int page, int size);

    PageResponse<VoucherResponse> filterByDiscountType(DiscountType discountType, int page, int size);

    PageResponse<VoucherResponse> searchByCode(String keyword, int page, int size);

    PageResponse<VoucherResponse> searchByDescription(String keyword, int page, int size);

    void delete(Long id);

    void restore(Long id);
}