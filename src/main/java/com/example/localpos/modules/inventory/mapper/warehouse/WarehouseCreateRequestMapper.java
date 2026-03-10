package com.example.localpos.modules.inventory.mapper.warehouse;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.inventory.dto.warehouse.request.WarehouseCreateRequest;
import com.example.localpos.modules.inventory.entity.Warehouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseCreateRequestMapper extends BaseMapper<Warehouse, WarehouseCreateRequest> {
}
