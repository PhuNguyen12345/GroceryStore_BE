package com.example.localpos.modules.inventory.mapper.batch;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.inventory.dto.batch.response.BatchResponse;
import com.example.localpos.modules.inventory.entity.InventoryBatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BatchResponseMapper extends BaseMapper<InventoryBatch, BatchResponse> {
    @Mapping(source = "warehouse.name", target = "warehouseName")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "productUnit.unitName", target = "unitName")
    @Mapping(source = "productUnit.product.name", target = "productName")
    @Override
    BatchResponse toDto(InventoryBatch entity);

    @Override
    List<BatchResponse> toDtoList(List<InventoryBatch> entities);
}
