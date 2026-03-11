package com.example.localpos.modules.crm.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.crm.dto.request.PromotionRequest;
import com.example.localpos.modules.crm.dto.response.PromotionResponse;
import com.example.localpos.modules.crm.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.CRMCtrl.PROMOTION)
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<String> savePromotion(@Valid @RequestBody PromotionRequest requestBody) {
        promotionService.savePromotion(requestBody);
        return ResponseEntity.ok("Lưu khuyến mãi thành công");
    }

    @GetMapping("/{id}")
    public PromotionResponse getById(@PathVariable Long id) {
        return promotionService.getById(id);
    }

    @GetMapping
    public PageResponse<PromotionResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return promotionService.getAll(page, size);
    }

    @GetMapping("/filter/active")
    public PageResponse<PromotionResponse> filterByIsActive(
            @RequestParam Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return promotionService.filterByIsActive(isActive, page, size);
    }

    @GetMapping("/search/name")
    public PageResponse<PromotionResponse> searchByName(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return promotionService.searchByName(keyword, page, size);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        promotionService.delete(id);
        return ResponseEntity.ok("Xóa khuyến mãi thành công");
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restore(@PathVariable Long id) {
        promotionService.restore(id);
        return ResponseEntity.ok("Khôi phục khuyến mãi thành công");
    }
}