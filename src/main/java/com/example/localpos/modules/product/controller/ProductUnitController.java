package com.example.localpos.modules.product.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.modules.product.dto.request.ProductUnitCreateRequest;
import com.example.localpos.modules.product.dto.request.ProductUnitUpdateRequest;
import com.example.localpos.modules.product.dto.response.ProductUnitResponse;
import com.example.localpos.modules.product.service.ProductUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.ProductCtrl.PRODUCT + "/units")
@RequiredArgsConstructor
public class ProductUnitController {
    private final ProductUnitService productUnitService;

    @PostMapping
    public ProductUnitResponse create(@RequestBody ProductUnitCreateRequest request) {
        return productUnitService.addProductUnit(request);
    }

    @PutMapping("/{id}")
    public ProductUnitResponse update(
            @PathVariable Long id,
            @RequestBody ProductUnitUpdateRequest request) {

        return productUnitService.updateProductUnit(id, request);
    }

    @GetMapping("/product/{productId}")
    public List<ProductUnitResponse> getUnits(@PathVariable Long productId) {
        return productUnitService.getUnitsByProduct(productId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        productUnitService.deleteProductUnit(id);
        return "Product unit deleted";
    }

    @PutMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        productUnitService.restoreProductUnit(id);
        return "Product unit restored";
    }
}
