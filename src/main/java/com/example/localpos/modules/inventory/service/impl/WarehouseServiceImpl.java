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
        Pageable pageable = PageRequest.of(page, size);
        Page<Warehouse> warehousePage = warehouseRepository.findAll(pageable);

        List<WarehouseResponse> responseList = warehousePage.getContent().stream()
                .map(warehouse -> new WarehouseResponse(
                        warehouse.getId(),
                        warehouse.getName(),
                        warehouse.getAddress(),
                        warehouse.getIsActive()
                )).toList();

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
        Warehouse warehouse = createMapper.toEntity(warehouseCreateRequest);
        log.info("Converted create request: {}", warehouse);

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        log.info("Warehouse saved: {}", savedWarehouse);

        WarehouseResponse warehouseResponse = responseMapper.toDto(savedWarehouse);
        log.info("Converted warehouse response: {}", warehouseResponse);
        return warehouseResponse;
    }

    @Override
    public WarehouseResponse updateWarehouse(WarehouseUpdateRequest warehouseUpdateRequest, Long id) {
        Warehouse existingWarehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse with id " + id + " not found"));

        log.info("Found warehouse: {}", existingWarehouse);
        updateMapper.updateEntity(existingWarehouse, warehouseUpdateRequest);
        log.info("Updating info: {}",  existingWarehouse);

        Warehouse savedWarehouse = warehouseRepository.save(existingWarehouse);
        log.info("Warehouse updated: {}", savedWarehouse);
        return responseMapper.toDto(savedWarehouse);
    }

    @Override
    public PageResponse<WarehouseResponse> findWarehousesByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Warehouse> warehousePage =  warehouseRepository.findByNameContainingIgnoreCase(name, pageable);

        List<Warehouse> pageContent = warehousePage.getContent();
        List<WarehouseResponse> responseList = responseMapper.toDtoList(pageContent);

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
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse with id " + id + " not found"));

        log.info("Warehouse to be deleted: {}", warehouse);
        warehouse.setIsActive(false);
        Warehouse deletedWarehouse = warehouseRepository.save(warehouse);
        log.info("Warehouse deleted: {}", deletedWarehouse);
    }

    @Override
    public WarehouseResponse restoreWarehouse(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse with id " + id + " not found"));

        warehouse.setIsActive(true);
        Warehouse restoredWarehouse = warehouseRepository.save(warehouse);
        log.info("Warehouse restored: {}", restoredWarehouse);
        return responseMapper.toDto(restoredWarehouse);
    }
}
