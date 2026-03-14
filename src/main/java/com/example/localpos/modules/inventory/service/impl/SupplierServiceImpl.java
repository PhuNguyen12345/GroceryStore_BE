package com.example.localpos.modules.inventory.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.supplier.request.SupplierCreateRequest;
import com.example.localpos.modules.inventory.dto.supplier.request.SupplierUpdateRequest;
import com.example.localpos.modules.inventory.dto.supplier.response.SupplierResponse;
import com.example.localpos.modules.inventory.entity.Supplier;
import com.example.localpos.modules.inventory.mapper.supplier.SupplierCreateRequestMapper;
import com.example.localpos.modules.inventory.mapper.supplier.SupplierResponseMapper;
import com.example.localpos.modules.inventory.mapper.supplier.SupplierUpdateRequestMapper;
import com.example.localpos.modules.inventory.repository.SupplierRepository;
import com.example.localpos.modules.inventory.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierCreateRequestMapper createRequestMapper;
    private final SupplierUpdateRequestMapper updateRequestMapper;
    private final SupplierResponseMapper supplierResponseMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierCreateRequestMapper createRequestMapper, SupplierUpdateRequestMapper updateRequestMapper,SupplierResponseMapper supplierResponseMapper) {
        this.supplierRepository = supplierRepository;
        this.createRequestMapper = createRequestMapper;
        this.updateRequestMapper = updateRequestMapper;
        this.supplierResponseMapper = supplierResponseMapper;
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
                        supplier.getAddress(),
                        supplier.getIsActive()
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
    public SupplierResponse addSupplier(SupplierCreateRequest supplierRequest) {
        //Map request to entity
        Supplier s = createRequestMapper.toEntity(supplierRequest);
        //log supplier entity to check data
        System.out.println( "Request supplier: " + s.toString());
        //save s into db
        Supplier savedSupplier = supplierRepository.save(s);
        //log saved supplier
        System.out.println( "Saved Supplier: " + savedSupplier.toString());
        //map saved supplier to response
        SupplierResponse supplierResponse = supplierResponseMapper.toDto(savedSupplier);
        //log response supplier
        System.out.println( "Response supplier: " + supplierResponse.toString());
        return supplierResponse;
    }

    @Override
    public SupplierResponse updateSupplier(Long id, SupplierUpdateRequest supplierRequest) {
        //find supplier by id
        Supplier existingSupplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier with id " + id + " not found"));
        //log supplier found by id
        log.info("Found supplier: {}", existingSupplier);
        //update from request mapper
        updateRequestMapper.updateEntity(existingSupplier, supplierRequest);
        //log supplier after ignore null from mapper
        log.info("Supplier with updating fields: {}", existingSupplier);
        //save supplier
        Supplier savedSupplier = supplierRepository.save(existingSupplier);
        log.info("Updated Supplier: {}", savedSupplier);
        return supplierResponseMapper.toDto(savedSupplier);
    }

    @Override
    public PageResponse<SupplierResponse> findSuppliersByName(String supplierName, int page, int size) {
        //pageable
        Pageable pageable = PageRequest.of(page, size);
        //find page contains Supplier containing the name
        Page<Supplier> supplierPage = supplierRepository.findByNameContainingIgnoreCase(supplierName, pageable);
        //Map to List Response
        List<SupplierResponse> responseList = supplierPage.getContent().stream().map(
                supplier -> new SupplierResponse(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getContactPerson(),
                        supplier.getPhone(),
                        supplier.getAddress(),
                        supplier.getIsActive()
                )).toList();

        //build into PageResponse
        return PageResponse.<SupplierResponse>builder()
                .content(responseList)
                .page(supplierPage.getNumber())
                .size(supplierPage.getSize())
                .totalElements(supplierPage.getTotalElements())
                .totalPages(supplierPage.getTotalPages())
                .build();
    }

    @Override
    public void deleteSupplier(Long id) {
        //find supplier by id
        Supplier s = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier with id " + id + " not found"));
        //log supplier to be delete
        log.info("Supplier to be delete : {}", s);
        //set s to be false
        s.setIsActive(false);
        //save s
        Supplier updatedSupplier = supplierRepository.save(s);
        //log
        log.info("Supplier deleted with id: {}", updatedSupplier);
    }
}
