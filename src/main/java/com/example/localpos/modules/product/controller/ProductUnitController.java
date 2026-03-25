package com.example.localpos.modules.product.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.ProductUnitCreateRequest;
import com.example.localpos.modules.product.dto.request.ProductUnitUpdateRequest;
import com.example.localpos.modules.product.dto.response.ProductUnitResponse;
import com.example.localpos.modules.product.service.ProductUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.ProductCtrl.PRODUCT + "/units")
@RequiredArgsConstructor
public class ProductUnitController {
    private final ProductUnitService productUnitService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductUnitCreateRequest request) {
        productUnitService.addProductUnit(request);
        return ResponseEntity.ok("Tạo đơn vị sản phẩm thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductUnitResponse> update(
            @PathVariable Long id,
            @RequestBody ProductUnitUpdateRequest request) {
        return ResponseEntity.ok(productUnitService.updateProductUnit(id, request));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<PageResponse<ProductUnitResponse>> getUnits(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        return ResponseEntity.ok(productUnitService.getUnitsByProduct(productId, page, size));
    }

    @GetMapping("/search/product")
    public ResponseEntity<List<ProductUnitResponse>> searchByProductName(
            @RequestParam String productName
    ) {
        return ResponseEntity.ok(productUnitService.searchUnitsByProductName(productName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productUnitService.deleteProductUnit(id);
        return ResponseEntity.ok("Xóa đơn vị sản phẩm thành công");
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restore(@PathVariable Long id) {
        productUnitService.restoreProductUnit(id);
        return ResponseEntity.ok("Khôi phục đơn vị sản phẩm thành công");
    }
}
