package com.example.localpos.modules.product.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.ProductUnitCreateRequest;
import com.example.localpos.modules.product.dto.request.ProductUnitUpdateRequest;
import com.example.localpos.modules.product.dto.response.ProductUnitResponse;

import java.util.List;

public interface ProductUnitService {
    ProductUnitResponse addProductUnit(ProductUnitCreateRequest request);

    ProductUnitResponse updateProductUnit(Long id, ProductUnitUpdateRequest request);

    PageResponse<ProductUnitResponse> getUnitsByProduct(Long productId, int page, int size);
    List<ProductUnitResponse> searchUnitsByProductName(String productName);

    void deleteProductUnit(Long id);
    void restoreProductUnit(Long id);
}
