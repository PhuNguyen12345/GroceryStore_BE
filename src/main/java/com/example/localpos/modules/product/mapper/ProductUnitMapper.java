package com.example.localpos.modules.product.mapper;

import com.example.localpos.modules.product.dto.request.ProductUnitCreateRequest;
import com.example.localpos.modules.product.dto.response.ProductUnitResponse;
import com.example.localpos.modules.product.entity.ProductUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductUnitMapper {

    ProductUnit toEntity(ProductUnitCreateRequest request);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ProductUnitResponse toDto(ProductUnit productUnit);
}