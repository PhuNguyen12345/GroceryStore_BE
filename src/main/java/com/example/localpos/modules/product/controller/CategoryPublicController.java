package com.example.localpos.modules.product.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.modules.product.dto.response.CategoryTreeResponse;
import com.example.localpos.modules.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.ProductCtrl.CATEGORY)
@RequiredArgsConstructor
public class CategoryPublicController {

    private final CategoryService categoryService;

    @GetMapping("/tree/active")
    public ResponseEntity<List<CategoryTreeResponse>> treeActive() {
        return ResponseEntity.ok(categoryService.getCategoryTreeActiveOnly());
    }

    @GetMapping("/tree")
    public ResponseEntity<List<CategoryTreeResponse>> tree() {
        return ResponseEntity.ok(categoryService.getCategoryTree());
    }
}
