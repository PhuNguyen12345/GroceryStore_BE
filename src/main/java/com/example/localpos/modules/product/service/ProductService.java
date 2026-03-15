package com.example.localpos.modules.product.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.ProductCreateRequest;
import com.example.localpos.modules.product.dto.request.ProductUpdateRequest;
import com.example.localpos.modules.product.dto.response.ProductResponse;



public interface ProductService {

    PageResponse<ProductResponse> getAllProducts(int page, int size);
    PageResponse<ProductResponse> findProductsByName(String name, int page, int size);
    PageResponse<ProductResponse> findProductsByCategory(Long categoryId, int page, int size);
    PageResponse<ProductResponse> findProductsByBrand(Long brandId, int page, int size);

    ProductResponse addProduct(ProductCreateRequest request);
    ProductResponse updateProduct(Long id, ProductUpdateRequest request);
    void deleteProduct(Long id);
    void restoreProduct(Long id);
}
