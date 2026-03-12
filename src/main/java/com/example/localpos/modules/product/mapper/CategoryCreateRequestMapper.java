package com.example.localpos.modules.product.mapper;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.product.dto.request.CategoryCreateRequest;
import com.example.localpos.modules.product.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryCreateRequestMapper extends BaseMapper<Category, CategoryCreateRequest> {
}
