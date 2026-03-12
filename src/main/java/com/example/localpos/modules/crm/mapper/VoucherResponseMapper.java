package com.example.localpos.modules.crm.mapper;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.crm.dto.response.VoucherResponse;
import com.example.localpos.modules.crm.entity.Voucher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoucherResponseMapper extends BaseMapper<Voucher, VoucherResponse> {
}