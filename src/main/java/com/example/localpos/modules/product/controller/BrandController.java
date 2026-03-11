package com.example.localpos.modules.product.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.BrandCreateRequest;
import com.example.localpos.modules.product.dto.request.BrandUpdateRequest;
import com.example.localpos.modules.product.dto.response.BrandResponse;
import com.example.localpos.modules.product.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.ProductCtrl.BRAND)
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    // GET ALL (admin) - paging + sort name ASC (đã xử lý ở repo)
    @GetMapping
    public ResponseEntity<PageResponse<BrandResponse>> getAllBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(brandService.getAllBrands(page, size));
    }

    // SEARCH BY NAME (admin) - paging
    @GetMapping("/search")
    public ResponseEntity<PageResponse<BrandResponse>> searchBrands(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(brandService.findBrandsByName(name, page, size));
    }

    // GET ACTIVE (client) - paging
    @GetMapping("/active")
    public ResponseEntity<PageResponse<BrandResponse>> getActiveBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(brandService.getActiveBrands(page, size));
    }

    // CREATE (admin)
    @PostMapping
    public ResponseEntity<String> createBrand(
            @Valid @RequestBody BrandCreateRequest request
    ) {
        brandService.addBrand(request);
        return ResponseEntity.ok("Tạo thương hiệu thành công");
    }

    // UPDATE (admin)
    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody BrandUpdateRequest request
    ) {
        return ResponseEntity.ok(brandService.updateBrand(id, request));
    }

    // SOFT DELETE (admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok("Xóa thương hiệu thành công");
    }

    // RESTORE (admin)
    @PutMapping("/{id}/restore")
    public ResponseEntity<String> restoreBrand(@PathVariable Long id) {
        brandService.restoreBrand(id);
        return ResponseEntity.ok("Khôi phục thương hiệu thành công");
    }
}
