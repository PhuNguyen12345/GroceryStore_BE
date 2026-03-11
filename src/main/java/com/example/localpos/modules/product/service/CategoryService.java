package com.example.localpos.modules.product.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.CategoryCreateRequest;
import com.example.localpos.modules.product.dto.request.CategoryUpdateRequest;
import com.example.localpos.modules.product.dto.response.CategoryResponse;
import com.example.localpos.modules.product.dto.response.CategoryTreeResponse;
import com.example.localpos.modules.product.mapper.CategoryUpdateRequestMapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    public PageResponse<CategoryResponse> getAllCategories(int page, int size);
    public PageResponse<CategoryResponse> findCategoriesByName(String name, int page, int size);
    public PageResponse<CategoryResponse> findCategoriesByParentId(Long parentId, int page, int size);

    CategoryResponse addCategory(CategoryCreateRequest request);
    CategoryResponse updateCategory(Long id, CategoryUpdateRequest request);
    void deleteCategory(Long id);
    void restoreCategory(Long id);

    List<CategoryTreeResponse> getCategoryTree();
    List<CategoryTreeResponse> getCategoryTreeActiveOnly(); // nếu client chỉ lấy active

}
