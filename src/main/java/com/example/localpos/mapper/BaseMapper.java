package com.example.localpos.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * @param <E> Entity type
 * @param <D> DTO type
 */
public interface BaseMapper<E,D> {
    // 1. Chuyển từ Entity sang DTO
    D toDto(E entity);

    // 2. Chuyển từ DTO sang Entity (Dùng cho lúc Tạo mới)
    E toEntity(D dto);

    // 3. Chuyển cả List
    List<D> toDtoList(List<E> entities);
    List<E> toEntityList(List<D> dtos);

    // 4. Update Entity từ DTO (Dùng cho lúc Update - PUT/PATCH)
    // Quan trọng: Chỉ update những trường khác null
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget E entity, D dto);
}
