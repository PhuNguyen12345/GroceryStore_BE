package com.example.localpos.modules.inventory.mapper;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.inventory.dto.request.SupplierRequest;
import com.example.localpos.modules.inventory.entity.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierRequestMapper extends BaseMapper<Supplier, SupplierRequest> {
}
