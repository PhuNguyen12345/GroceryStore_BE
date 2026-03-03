package com.example.localpos.modules.inventory.service.impl;

import com.example.localpos.common.PageResponse;
import com.example.localpos.modules.inventory.dto.request.SupplierRequest;
import com.example.localpos.modules.inventory.dto.response.SupplierResponse;
import com.example.localpos.modules.inventory.entity.Supplier;
import com.example.localpos.modules.inventory.repository.SupplierRepository;
import com.example.localpos.modules.inventory.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public PageResponse<SupplierResponse> getAllSuppliers(int page, int size) {
        //pageable
        Pageable pageable = PageRequest.of(page, size);
        //Query to get Page<Suppllier>
        Page<Supplier> supplierPage = supplierRepository.findAll(pageable);
        //map to PageResponse
        List<SupplierResponse> responseList = supplierPage.getContent().stream()
                .map(supplier -> new SupplierResponse(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getContactPerson(),
                        supplier.getPhone(),
                        supplier.getAddress()
                )).toList();

        return PageResponse.<SupplierResponse>builder()
                .content(responseList)
                .page(supplierPage.getNumber())
                .size(supplierPage.getSize())
                .totalElements(supplierPage.getTotalElements())
                .totalPages(supplierPage.getTotalPages())
                .build();
    }

    @Override
    public SupplierResponse addSupplier(SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = new SupplierResponse();
        return null;
    }
}
