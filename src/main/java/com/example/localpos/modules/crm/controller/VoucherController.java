package com.example.localpos.modules.crm.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.enums.DiscountType;
import com.example.localpos.modules.crm.dto.request.VoucherCreateRequest;
import com.example.localpos.modules.crm.dto.request.VoucherUpdateRequest;
import com.example.localpos.modules.crm.dto.response.VoucherResponse;
import com.example.localpos.modules.crm.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.CRMCtrl.VOUCHER)
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    // CREATE
    @PostMapping
    public VoucherResponse createVoucher(@RequestBody VoucherCreateRequest request) {
        return voucherService.create(request);
    }

    // UPDATE
    @PutMapping("/{id}")
    public VoucherResponse updateVoucher(
            @PathVariable Long id,
            @RequestBody VoucherUpdateRequest request
    ) {
        return voucherService.update(id, request);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public VoucherResponse getVoucherById(@PathVariable Long id) {
        return voucherService.getById(id);
    }

    // GET BY CODE
    @GetMapping("/code/{code}")
    public VoucherResponse getVoucherByCode(@PathVariable String code) {
        return voucherService.getByCode(code);
    }

    // GET ALL
    @GetMapping
    public PageResponse<VoucherResponse> getAllVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return voucherService.getAll(page, size);
    }

    // FILTER ACTIVE
    @GetMapping("/filter/active")
    public PageResponse<VoucherResponse> filterByActive(
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return voucherService.filterByIsActive(isActive, page, size);
    }

    // FILTER DISCOUNT TYPE
    @GetMapping("/filter/type")
    public PageResponse<VoucherResponse> filterByDiscountType(
            @RequestParam DiscountType discountType,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return voucherService.filterByDiscountType(discountType, page, size);
    }

    // SEARCH CODE
    @GetMapping("/search/code")
    public PageResponse<VoucherResponse> searchByCode(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return voucherService.searchByCode(keyword, page, size);
    }

    // SEARCH DESCRIPTION
    @GetMapping("/search/description")
    public PageResponse<VoucherResponse> searchByDescription(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return voucherService.searchByDescription(keyword, page, size);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public void deleteVoucher(@PathVariable Long id) {
        voucherService.delete(id);
    }

    // RESTORE
    @PatchMapping("/{id}/restore")
    public void restoreVoucher(@PathVariable Long id) {
        voucherService.restore(id);
    }
}