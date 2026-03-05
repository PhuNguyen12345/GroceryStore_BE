package com.example.localpos.modules.product.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.CategoryCreateRequest;
import com.example.localpos.modules.product.dto.request.CategoryUpdateRequest;
import com.example.localpos.modules.product.dto.response.CategoryResponse;
import com.example.localpos.modules.product.dto.response.CategoryTreeResponse;
import com.example.localpos.modules.product.entity.Category;
import com.example.localpos.modules.product.mapper.CategoryCreateRequestMapper;
import com.example.localpos.modules.product.mapper.CategoryResponseMapper;
import com.example.localpos.modules.product.mapper.CategoryUpdateRequestMapper;
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

    // Repository dùng để truy cập database
    private final CategoryRepository categoryRepository;

    // Mapper dùng để convert request -> entity
    private final CategoryCreateRequestMapper categoryCreateRequestMapper;

    // Mapper dùng để update entity
    private final CategoryUpdateRequestMapper categoryUpdateRequestMapper;

    // Mapper convert entity -> response
    private final CategoryResponseMapper categoryResponseMapper;


    //Lấy toàn bộ category có phân trang
    @Override
    public PageResponse<CategoryResponse> getAllCategories(int page, int size) {

        // tạo pageable object (page + size)
        Pageable pageable = PageRequest.of(page, size);

        // lấy dữ liệu từ database
        Page<Category> categoryPage = categoryRepository.findAllByOrderByNameAsc(pageable);

        // convert entity -> response
        List<CategoryResponse> responseList = categoryPage.getContent().stream()
                .map(categoryResponseMapper::toDto).toList();

        // trả về PageResponse
        return PageResponse.<CategoryResponse>builder()
                .content(responseList)
                .page(categoryPage.getNumber())
                .size(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .build();
    }

    // Tìm category theo name (search)
    @Override
    public PageResponse<CategoryResponse> findCategoriesByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // repository query search name
        Page<Category> categoryPage =
                categoryRepository.findByNameContainingIgnoreCase(name,pageable);

        // convert entity -> response
        List<CategoryResponse> responseList = categoryPage.getContent().stream()
                .map(categoryResponseMapper::toDto).toList();

        return PageResponse.<CategoryResponse>builder()
                .content(responseList)
                .page(categoryPage.getNumber())
                .size(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .build();
    }


    //Lấy category theo parentId
    //     * (dùng để lấy danh mục con)
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


    //Tạo category mới
    @Override
    public CategoryResponse addCategory(CategoryCreateRequest request) {
        // convert request -> entity
        Category category = categoryCreateRequestMapper.toEntity(request);

        // nếu có parentId thì set parent
        if(request.getParentId() != null) {

            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Category parent not found"));

            category.setParent(parent);
        }

        // auto generate slug nếu client không truyền
        if (request.getSlug() == null || request.getSlug().isBlank()) {
            category.setSlug(toSlug(request.getName()));
        }
        // check slug unique
        if (category.getSlug() != null && categoryRepository.existsBySlug(category.getSlug())) {
            throw new IllegalArgumentException("Slug already exists");
        }

        // nếu client không truyền isActive thì mặc định true
        if(category.getIsActive() == null) {
            category.setIsActive(true);
        }

        // save xuống database
        Category saved = categoryRepository.save(category);

        // convert entity -> response
        return categoryResponseMapper.toDto(saved);
    }

    //Update category chỉ update field nào != null
    @Override
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        // tìm category cần update
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Category not found"));

        // update name
        if(request.getName() != null) {
            category.setName(request.getName());
        }

        // update description
        if(request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }

        // update trạng thái active
        if(request.getIsActive() != null) {
            category.setIsActive(request.getIsActive());
        }

        // update parent
        if (request.getParentId() != null) {

            // tránh set parent = chính nó
            if (request.getParentId().equals(id)) {
                throw new RuntimeException("Category cannot be parent of itself");
            }

            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Category parent not found"));
            if (isCycle(parent, id)) {
                throw new RuntimeException("Category hierarchy cycle detected");
            }
            category.setParent(parent);
        }

        // update slug
        String targetSlug = null;

        if (request.getSlug() != null) {
            targetSlug = request.getSlug().isBlank() ? null : request.getSlug();
        } else if (request.getName() != null) {
            targetSlug = toSlug(request.getName());
        }

        if (targetSlug != null) {
            Optional<Category> existing = categoryRepository.findBySlug(targetSlug);
            if (existing.isPresent() && !existing.get().getId().equals(id)) {
                throw new RuntimeException("Slug already exists");
            }
            category.setSlug(targetSlug);
        }

        Category saved = categoryRepository.save(category);

        return categoryResponseMapper.toDto(saved);
    }



    //Xóa category
    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (Boolean.FALSE.equals(category.getIsActive())) {
            return; // đã inactive rồi thì thôi
        }

        category.setIsActive(false);
        categoryRepository.save(category);
    }

    @Override
    public void restoreCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setIsActive(true);
        categoryRepository.save(category);
    }

    //Lấy cây category (category tree)
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

    //Lấy category tree nhưng chỉ lấy category active
    @Override
    public List<CategoryTreeResponse> getCategoryTreeActiveOnly() {
        List<Category> all = categoryRepository.findAll();

        Map<Long, CategoryTreeResponse> map = new HashMap<>();
        List<CategoryTreeResponse> roots = new ArrayList<>();

        // chỉ build node cho category active
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

        // build tree
        for (Category c : all) {

            if (!Boolean.TRUE.equals(c.getIsActive())) continue;

            CategoryTreeResponse node = map.get(c.getId());

            if (c.getParent() == null ||
                    !map.containsKey(c.getParent().getId())) {

                roots.add(node);

            } else {

                map.get(c.getParent().getId())
                        .getChildren()
                        .add(node);
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

        // bỏ dấu tiếng Việt + ký tự đặc biệt
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
