package com.example.localpos.modules.product.mapper;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.product.dto.response.ProductResponse;
import com.example.localpos.modules.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper extends BaseMapper<Product, ProductResponse> {
    @Override
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "imageUrl", source = "imageUrl")
    ProductResponse toDto(Product entity);
}
