package com.example.localpos.modules.product.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.CategoryCreateRequest;
import com.example.localpos.modules.product.dto.request.CategoryUpdateRequest;
import com.example.localpos.modules.product.dto.response.CategoryResponse;
import com.example.localpos.modules.product.dto.response.CategoryTreeResponse;
import com.example.localpos.modules.product.entity.Category;
import com.example.localpos.modules.product.mapper.CategoryCreateRequestMapper;
import com.example.localpos.modules.product.mapper.CategoryResponseMapper;
import com.example.localpos.modules.product.repository.CategoryRepository;
import com.example.localpos.modules.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryCreateRequestMapper categoryCreateRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public PageResponse<CategoryResponse> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categoryPage = categoryRepository.findAllByOrderByNameAsc(pageable);

        List<CategoryResponse> responseList = categoryPage.getContent().stream()
                .map(categoryResponseMapper::toDto)
                .toList();

        return PageResponse.<CategoryResponse>builder()
                .content(responseList)
                .page(categoryPage.getNumber())
                .size(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<CategoryResponse> findCategoriesByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categoryPage =
                categoryRepository.findByNameContainingIgnoreCase(name, pageable);

        List<CategoryResponse> responseList = categoryPage.getContent().stream()
                .map(categoryResponseMapper::toDto)
                .toList();

        return PageResponse.<CategoryResponse>builder()
                .content(responseList)
                .page(categoryPage.getNumber())
                .size(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<CategoryResponse> findCategoriesByParentId(Long parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findByParent_Id(parentId, pageable);

        List<CategoryResponse> responseList = categoryPage.getContent().stream()
                .map(categoryResponseMapper::toDto)
                .toList();

        return PageResponse.<CategoryResponse>builder()
                .content(responseList)
                .page(categoryPage.getNumber())
                .size(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .build();
    }

    @Override
    public CategoryResponse addCategory(CategoryCreateRequest request) {

        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Tên danh mục đã tồn tại");
        }

        Category category = categoryCreateRequestMapper.toEntity(request);

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Không tìm thấy danh mục cha"));

            category.setParent(parent);
        }

        if (request.getSlug() == null || request.getSlug().isBlank()) {
            category.setSlug(toSlug(request.getName()));
        }

        if (category.getSlug() != null && categoryRepository.existsBySlug(category.getSlug())) {
            throw new IllegalArgumentException("Slug đã tồn tại");
        }

        if (category.getIsActive() == null) {
            category.setIsActive(true);
        }

        Category saved = categoryRepository.save(category);
        return categoryResponseMapper.toDto(saved);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Không tìm thấy danh mục"));

        if (request.getName() != null &&
                categoryRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new RuntimeException("Tên danh mục đã tồn tại");
        }

        if (request.getName() != null) {
            category.setName(request.getName());
        }

        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }

        if (request.getIsActive() != null) {
            category.setIsActive(request.getIsActive());
        }

        if (request.getParentId() != null) {
            if (request.getParentId().equals(id)) {
                throw new RuntimeException("Danh mục không thể là danh mục cha của chính nó");
            }

            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Không tìm thấy danh mục cha"));

            if (isCycle(parent, id)) {
                throw new RuntimeException("Phát hiện vòng lặp trong cây danh mục");
            }

            category.setParent(parent);
        }

        String targetSlug = null;

        if (request.getSlug() != null) {
            targetSlug = request.getSlug().isBlank() ? null : request.getSlug();
        } else if (request.getName() != null) {
            targetSlug = toSlug(request.getName());
        }

        if (targetSlug != null) {
            Optional<Category> existing = categoryRepository.findBySlug(targetSlug);
            if (existing.isPresent() && !existing.get().getId().equals(id)) {
                throw new RuntimeException("Slug đã tồn tại");
            }
            category.setSlug(targetSlug);
        }

        Category saved = categoryRepository.save(category);
        return categoryResponseMapper.toDto(saved);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));

        if (Boolean.FALSE.equals(category.getIsActive())) {
            return;
        }

        category.setIsActive(false);
        categoryRepository.save(category);
    }

    @Override
    public void restoreCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));

        category.setIsActive(true);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryTreeResponse> getCategoryTree() {
        List<Category> roots = categoryRepository.findByParentIsNullOrderByNameAsc();

        return roots.stream()
                .map(this::buildTree)
                .toList();
    }

    private CategoryTreeResponse buildTree(Category category) {
        CategoryTreeResponse node = CategoryTreeResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .isActive(category.getIsActive())
                .children(new ArrayList<>())
                .build();

        List<Category> children =
                categoryRepository.findByParent_IdOrderByNameAsc(category.getId());

        for (Category child : children) {
            node.getChildren().add(buildTree(child));
        }

        return node;
    }

    @Override
    public List<CategoryTreeResponse> getCategoryTreeActiveOnly() {
        List<Category> all = categoryRepository.findAll();

        Map<Long, CategoryTreeResponse> map = new HashMap<>();
        List<CategoryTreeResponse> roots = new ArrayList<>();

        for (Category c : all) {
            if (Boolean.TRUE.equals(c.getIsActive())) {
                map.put(c.getId(),
                        CategoryTreeResponse.builder()
                                .id(c.getId())
                                .name(c.getName())
                                .slug(c.getSlug())
                                .isActive(c.getIsActive())
                                .children(new ArrayList<>())
                                .build()
                );
            }
        }

        for (Category c : all) {
            if (!Boolean.TRUE.equals(c.getIsActive())) continue;

            CategoryTreeResponse node = map.get(c.getId());

            if (c.getParent() == null || !map.containsKey(c.getParent().getId())) {
                roots.add(node);
            } else {
                map.get(c.getParent().getId()).getChildren().add(node);
            }
        }

        return roots;
    }

    private boolean isCycle(Category newParent, Long currentCategoryId) {
        Category p = newParent;
        while (p != null) {
            if (p.getId().equals(currentCategoryId)) return true;
            p = p.getParent();
        }
        return false;
    }

    private String toSlug(String input) {
        if (input == null) return null;

        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replace("đ", "d")
                .replace("Đ", "D");

        return normalized.trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}
