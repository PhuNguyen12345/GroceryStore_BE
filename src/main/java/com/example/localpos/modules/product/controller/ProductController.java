package com.example.localpos.modules.product.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.CategoryCreateRequest;
import com.example.localpos.modules.product.dto.request.CategoryUpdateRequest;
import com.example.localpos.modules.product.dto.request.ProductCreateRequest;
import com.example.localpos.modules.product.dto.request.ProductUpdateRequest;
import com.example.localpos.modules.product.dto.response.CategoryResponse;
import com.example.localpos.modules.product.dto.response.ProductResponse;
import com.example.localpos.modules.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.ProductCtrl.PRODUCT)
@RequiredArgsConstructor

public class ProductController {
    private final ProductService productService;

    // GET ALL PRODUCTS
    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    // SEARCH PRODUCT BY NAME
    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProductResponse>> searchProducts(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.findProductsByName(name, page, size));
    }

    // FIND BY CATEGORY
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<PageResponse<ProductResponse>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.findProductsByCategory(categoryId, page, size));
    }

    // FIND BY BRAND
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<PageResponse<ProductResponse>> getProductsByBrand(
            @PathVariable Long brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.findProductsByBrand(brandId, page, size));
    }

    // CREATE PRODUCT
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse response = productService.addProduct(request);
        return ResponseEntity.ok(response);
    }

    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    // SOFT DELETE PRODUCT
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công");
    }

    // RESTORE PRODUCT
    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restoreProduct(@PathVariable Long id) {
        productService.restoreProduct(id);
        return ResponseEntity.ok("Khôi phục sản phẩm thành công");
    }
}
