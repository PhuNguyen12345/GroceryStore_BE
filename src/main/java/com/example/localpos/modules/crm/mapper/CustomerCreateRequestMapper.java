package com.example.localpos.modules.crm.mapper;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.crm.dto.request.CustomerCreateRequest;
import com.example.localpos.modules.crm.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerCreateRequestMapper extends BaseMapper<Customer, CustomerCreateRequest> {
}