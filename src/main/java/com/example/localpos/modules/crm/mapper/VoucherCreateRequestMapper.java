package com.example.localpos.modules.crm.mapper;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.crm.dto.request.VoucherCreateRequest;
import com.example.localpos.modules.crm.entity.Voucher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoucherCreateRequestMapper extends BaseMapper<Voucher, VoucherCreateRequest> {
}