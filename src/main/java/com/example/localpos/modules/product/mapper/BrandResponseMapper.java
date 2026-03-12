package com.example.localpos.modules.product.mapper;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.product.dto.response.BrandResponse;
import com.example.localpos.modules.product.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandResponseMapper extends BaseMapper<Brand, BrandResponse> {
}
