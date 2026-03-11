package com.example.localpos.modules.inventory.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.inventory.dto.warehouse.request.WarehouseCreateRequest;
import com.example.localpos.modules.inventory.dto.warehouse.request.WarehouseUpdateRequest;
import com.example.localpos.modules.inventory.dto.warehouse.response.WarehouseResponse;
import com.example.localpos.modules.inventory.entity.Warehouse;
import com.example.localpos.modules.inventory.mapper.warehouse.WarehouseCreateRequestMapper;
import com.example.localpos.modules.inventory.mapper.warehouse.WarehouseResponseMapper;
import com.example.localpos.modules.inventory.mapper.warehouse.WarehouseUpdateRequestMapper;
import com.example.localpos.modules.inventory.repository.WarehouseRepository;
import com.example.localpos.modules.inventory.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseCreateRequestMapper createMapper;
    private final WarehouseUpdateRequestMapper updateMapper;
    private final WarehouseResponseMapper  responseMapper;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, WarehouseCreateRequestMapper createMapper,
                                WarehouseUpdateRequestMapper updateMapper, WarehouseResponseMapper responseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.createMapper = createMapper;
        this.updateMapper = updateMapper;
        this.responseMapper = responseMapper;
    }

    @Override
    public PageResponse<WarehouseResponse> getAllWarehouses(int page, int size) {
        //define pageable
        Pageable pageable = PageRequest.of(page, size);
        //Query to get all warehouses
        Page<Warehouse> warehousePage = warehouseRepository.findAll(pageable);
        //map to PageResponse
        List<WarehouseResponse> responseList = warehousePage.getContent().stream()
                .map(warehouse -> new WarehouseResponse(
                        warehouse.getId(),
                        warehouse.getName(),
                        warehouse.getAddress(),
                        warehouse.getIsActive()
                )).toList();

        //return PageResposne
        return PageResponse.<WarehouseResponse>builder()
                .content(responseList)
                .page(warehousePage.getNumber())
                .size(warehousePage.getSize())
                .totalElements(warehousePage.getTotalElements())
                .totalPages(warehousePage.getTotalPages())
                .build();
    }

    @Override
    public WarehouseResponse addWarehouse(WarehouseCreateRequest warehouseCreateRequest) {
        //Map request to entity
        Warehouse warehouse = createMapper.toEntity(warehouseCreateRequest);
        //log data
        log.info("Converted create request: {}", warehouse);
        //save to db
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        //log saved warehouse
        log.info("Warehouse saved: {}", savedWarehouse);
        //map saved warehouse to response
        WarehouseResponse warehouseResponse = responseMapper.toDto(savedWarehouse);
        //log
        log.info("Converted warehouse response: {}", warehouseResponse);
        //return
        return warehouseResponse;
    }

    @Override
    public WarehouseResponse updateWarehouse(WarehouseUpdateRequest warehouseUpdateRequest, Long id) {
        //find by id
        Warehouse existingWarehouse = warehouseRepository.findById(id).orElseThrow(() -> new RuntimeException("Warehouse with id "+id+" not found"));
        //log found warehouse 
        log.info("Found warehouse: {}", existingWarehouse); 
        //update by mapper 
        updateMapper.updateEntity(existingWarehouse, warehouseUpdateRequest); 
        //log warehouse necessary info to update 
        log.info("Updating info: {}",  existingWarehouse);
        //save to db 
        Warehouse savedWarehouse = warehouseRepository.save(existingWarehouse);
        //log 
        log.info("Warehouse updated: {}", savedWarehouse);
        return responseMapper.toDto(savedWarehouse);
    }

    @Override
    public PageResponse<WarehouseResponse> findWarehousesByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        //find all by name 
        Page<Warehouse> warehousePage =  warehouseRepository.findByNameContainingIgnoreCase(name, pageable);
        //get content 
        List<Warehouse> pageContent = warehousePage.getContent(); 
        //convert to list response 
        List<WarehouseResponse> responseList = responseMapper.toDtoList(pageContent);
        //return PageResponse 
        return PageResponse.<WarehouseResponse>builder()
                .content(responseList)
                .page(warehousePage.getNumber())
                .size(warehousePage.getSize())
                .totalElements(warehousePage.getTotalElements())
                .totalPages(warehousePage.getTotalPages())
                .build();
    }

    @Override
    public void deleteWarehouse(Long id) {
        //find warehouse by id
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new RuntimeException("Warehouse with id "+id+" not found"));
        //log
        log.info("Warehouse to be deleted: {}", warehouse);
        //set active to be false
        warehouse.setIsActive(false);
        //save warehouse
        Warehouse deletedWarehouse = warehouseRepository.save(warehouse);
        //log
        log.info("Warehouse deleted: {}", deletedWarehouse);
    }
}
