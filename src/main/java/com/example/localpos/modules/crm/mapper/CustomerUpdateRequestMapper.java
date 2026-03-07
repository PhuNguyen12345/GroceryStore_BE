package com.example.localpos.modules.crm.mapper;
import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.crm.dto.request.CustomerUpdateRequest;
import com.example.localpos.modules.crm.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerUpdateRequestMapper extends BaseMapper<Customer, CustomerUpdateRequest> {
}