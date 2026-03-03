package com.example.localpos.modules.inventory.service;

import com.example.localpos.common.PageResponse;
import com.example.localpos.modules.inventory.dto.request.SupplierRequest;
import com.example.localpos.modules.inventory.dto.response.SupplierResponse;
import com.example.localpos.modules.inventory.entity.Supplier;
import org.springframework.data.domain.PageRequest;

public interface SupplierService {
    public PageResponse<SupplierResponse> getAllSuppliers(int page, int size);
    public SupplierResponse addSupplier(SupplierRequest supplierRequest);
}
