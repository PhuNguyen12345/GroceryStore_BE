package com.example.localpos.mapper;

import com.example.localpos.modules.product.dto.response.ProductResponseExample;
import com.example.localpos.modules.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapperExample extends BaseMapper<Product, ProductResponseExample> {
    //Các cột còn lại trùng tên = auto map
    //Đối tượng Brand brand, cột id
    @Mapping(source = "brand.id", target = "brandId")
    //Đối tượng Brand brand, cột name
    @Mapping(source = "brand.name", target = "brandName")
    //Đối tượng Category category, cột name
    @Mapping(source = "category.name", target = "categoryName")
    @Override
    ProductResponseExample toDto(Product entity);
}
