package com.example.localpos.modules.inventory.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.supplier.request.SupplierCreateRequest;
import com.example.localpos.modules.inventory.dto.supplier.request.SupplierUpdateRequest;
import com.example.localpos.modules.inventory.dto.supplier.response.SupplierResponse;

public interface SupplierService {
    public PageResponse<SupplierResponse> getAllSuppliers(int page, int size);
    public SupplierResponse addSupplier(SupplierCreateRequest supplierRequest);
    public SupplierResponse updateSupplier(Long id, SupplierUpdateRequest supplierRequest);
    public PageResponse<SupplierResponse> findSuppliersByName(String supplierName, int page, int size);
    void deleteSupplier(Long id);
}
