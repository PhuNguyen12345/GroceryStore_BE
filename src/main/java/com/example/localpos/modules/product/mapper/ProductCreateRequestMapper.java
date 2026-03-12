package com.example.localpos.modules.product.mapper;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.product.dto.request.ProductCreateRequest;
import com.example.localpos.modules.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCreateRequestMapper extends BaseMapper<Product, ProductCreateRequest> {

    @Override
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "brand.id", source = "brandId")
    Product toEntity(ProductCreateRequest dto);

}
