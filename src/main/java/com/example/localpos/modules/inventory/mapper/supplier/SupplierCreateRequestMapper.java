package com.example.localpos.modules.inventory.mapper.supplier;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.inventory.dto.supplier.request.SupplierCreateRequest;
import com.example.localpos.modules.inventory.entity.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierCreateRequestMapper extends BaseMapper<Supplier, SupplierCreateRequest> {
}
