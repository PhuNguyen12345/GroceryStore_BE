package com.example.localpos.modules.crm.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.enums.DiscountType;
import com.example.localpos.modules.crm.dto.request.VoucherRequest;
import com.example.localpos.modules.crm.dto.response.VoucherResponse;
import com.example.localpos.modules.crm.service.VoucherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(ApiPaths.CRMCtrl.VOUCHER)
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    // CREATE, UPDATE
    @PostMapping
    public ResponseEntity<String> savedVoucher(@Valid @RequestBody VoucherRequest request) {
        voucherService.saveVoucher(request);
        return ResponseEntity.ok("Lưu mã giảm giá thành công");
    }

    // GET BY ID
    @GetMapping("/{id}")
    public VoucherResponse getVoucherById(@PathVariable Long id) {
        return voucherService.getById(id);
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

    // GET APPLICABLE VOUCHERS FOR CHECKOUT
    @GetMapping("/applicable")
    public PageResponse<VoucherResponse> getApplicableVouchers(
            @RequestParam BigDecimal orderValue,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return voucherService.getApplicableVouchers(orderValue, page, size);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVoucher(@PathVariable Long id) {
        voucherService.delete(id);
        return ResponseEntity.ok("Xóa mã giảm giá thành công");
    }

    // RESTORE
    @PutMapping("/{id}/restore")
    public ResponseEntity<String> restoreVoucher(@PathVariable Long id) {
        voucherService.restore(id);
        return ResponseEntity.ok("Khôi phục mã giảm giá thành công");
    }
}
