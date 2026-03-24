package com.example.localpos.modules.crm.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.enums.CustomerTier;
import com.example.localpos.modules.crm.dto.request.CustomerRequest;
import com.example.localpos.modules.crm.dto.response.CustomerResponse;
import com.example.localpos.modules.crm.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.CRMCtrl.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // CREATE, UPDATE
    @PostMapping
    public ResponseEntity<CustomerResponse> saveCustomer(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.saveCustomer(request));
    }

    // GET BY ID
    @GetMapping("/{id:\\d+}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    // GET BY PHONE
    @GetMapping("/phone/{phone}")
    public CustomerResponse getCustomerByPhone(@PathVariable String phone) {
        return customerService.getByPhone(phone);
    }

    // GET ALL
    @GetMapping
    public PageResponse<CustomerResponse> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return customerService.getAll(page, size);
    }

    // FILTER ACTIVE
    @GetMapping("/filter/active")
    public PageResponse<CustomerResponse> filterByActive(
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return customerService.filterByIsActive(isActive, page, size);
    }

    // FILTER TIER
    @GetMapping("/filter/tier")
    public PageResponse<CustomerResponse> filterByTier(
            @RequestParam CustomerTier tier,
            @RequestParam int page,
            @RequestParam int size) {

        return customerService.filterByTier(tier, page, size);
    }

    // SEARCH NAME
    @GetMapping("/search/name")
    public PageResponse<CustomerResponse> searchByName(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return customerService.searchByFullName(keyword, page, size);
    }

    // SEARCH PHONE
    @GetMapping("/search/phone")
    public PageResponse<CustomerResponse> searchByPhone(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return customerService.searchByPhone(keyword, page, size);
    }

    // SEARCH EMAIL
    @GetMapping("/search/email")
    public PageResponse<CustomerResponse> searchByEmail(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return customerService.searchByEmail(keyword, page, size);
    }

    // SEARCH WITH FILTERS
    @GetMapping("/search")
    public PageResponse<CustomerResponse> searchCustomers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) CustomerTier tier,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return customerService.searchCustomers(keyword, isActive, tier, page, size);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok("XÃ³a khÃ¡ch hÃ ng thÃ nh cÃ´ng");
    }

    // RESTORE
    @PutMapping("/{id}/restore")
    public ResponseEntity<String> restoreCustomer(@PathVariable Long id) {
        customerService.restore(id);
        return ResponseEntity.ok("KhÃ´i phá»¥c khÃ¡ch hÃ ng thÃ nh cÃ´ng");
    }
}

