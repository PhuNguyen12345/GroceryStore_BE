package com.example.localpos.modules.product.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.ProductCreateRequest;
import com.example.localpos.modules.product.dto.request.ProductUpdateRequest;
import com.example.localpos.modules.product.dto.response.CategoryResponse;
import com.example.localpos.modules.product.dto.response.ProductResponse;
import com.example.localpos.modules.product.entity.Category;
import com.example.localpos.modules.product.entity.Product;
import com.example.localpos.modules.product.mapper.ProductCreateRequestMapper;
import com.example.localpos.modules.product.mapper.ProductResponseMapper;
import com.example.localpos.modules.product.mapper.ProductUpdateRequestMapper;
import com.example.localpos.modules.product.repository.BrandRepository;
import com.example.localpos.modules.product.repository.CategoryRepository;
import com.example.localpos.modules.product.repository.ProductRepository;
import com.example.localpos.modules.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    private final ProductCreateRequestMapper createRequestMapper;
    private final ProductUpdateRequestMapper updateRequestMapper;
    private final ProductResponseMapper productResponseMapper;

    @Override
    public PageResponse<ProductResponse> getAllProducts(int page, int size) {
        // tạo pageable object (page + size)
        Pageable pageable = PageRequest.of(page, size);

        // lấy dữ liệu từ database
        Page<Product> productPage = productRepository.findAllByOrderByNameAsc(pageable);
        // convert entity -> response
        List<ProductResponse> responseList = productPage.getContent().stream()
                .map(productResponseMapper::toDto).toList();

        // trả về PageResponse
        return PageResponse.<ProductResponse>builder()
                .content(responseList)
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<ProductResponse> findProductsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(name, pageable);
        List<ProductResponse> responseList = productPage.getContent()
                .stream()
                .map(productResponseMapper::toDto).toList();
        return PageResponse.<ProductResponse>builder()
                .content(responseList)
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<ProductResponse> findProductsByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findByCategory_Id(categoryId, pageable);

        List<ProductResponse> responseList = productPage.getContent()
                .stream()
                .map(productResponseMapper::toDto)
                .toList();

        return PageResponse.<ProductResponse>builder()
                .content(responseList)
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<ProductResponse> findProductsByBrand(Long brandId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findByBrand_Id(brandId, pageable);

        List<ProductResponse> responseList = productPage.getContent()
                .stream()
                .map(productResponseMapper::toDto)
                .toList();

        return PageResponse.<ProductResponse>builder()
                .content(responseList)
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .build();
    }

    @Override
    public ProductResponse addProduct(ProductCreateRequest request) {
        Product product = createRequestMapper.toEntity(request);

        // set category
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        // set brand
        if (request.getBrandId() != null) {
            product.setBrand(
                    brandRepository.findById(request.getBrandId())
                            .orElseThrow(() -> new RuntimeException("Brand not found"))
            );
        }

        Product savedProduct = productRepository.save(product);

        return productResponseMapper.toDto(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        updateRequestMapper.updateEntity(request, product);

        if (request.getCategoryId() != null) {
            product.setCategory(
                    categoryRepository.findById(request.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"))
            );
        }

        if (request.getBrandId() != null) {
            product.setBrand(
                    brandRepository.findById(request.getBrandId())
                            .orElseThrow(() -> new RuntimeException("Brand not found"))
            );
        }

        Product savedProduct = productRepository.save(product);

        return productResponseMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setIsActive(false);

        productRepository.save(product);
    }

    @Override
    public void restoreProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setIsActive(true);

        productRepository.save(product);
    }

}
