package com.example.localpos.modules.product.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.BrandCreateRequest;
import com.example.localpos.modules.product.dto.request.BrandUpdateRequest;
import com.example.localpos.modules.product.dto.response.BrandResponse;

public interface BrandService {
    // lấy tất cả brand (admin)
    PageResponse<BrandResponse> getAllBrands(int page, int size);

    // search brand theo name
    PageResponse<BrandResponse> findBrandsByName(String name, int page, int size);

    // lấy brand active (client)
    PageResponse<BrandResponse> getActiveBrands(int page, int size);

    // create brand
    BrandResponse addBrand(BrandCreateRequest request);

    // update brand
    BrandResponse updateBrand(Long id, BrandUpdateRequest request);

    // soft delete
    void deleteBrand(Long id);

    // restore brand
    void restoreBrand(Long id);
}
