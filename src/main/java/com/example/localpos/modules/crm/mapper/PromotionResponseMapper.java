package com.example.localpos.modules.crm.mapper;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.crm.dto.response.PromotionResponse;
import com.example.localpos.modules.crm.entity.Promotion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PromotionResponseMapper extends BaseMapper<Promotion, PromotionResponse> {
}