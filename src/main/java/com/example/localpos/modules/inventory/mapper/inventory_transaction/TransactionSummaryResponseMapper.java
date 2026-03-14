package com.example.localpos.modules.inventory.mapper.inventory_transaction;

import com.example.localpos.mapper.BaseMapper;
import com.example.localpos.modules.inventory.dto.transaction.summary.response.TransactionSummaryResponse;
import com.example.localpos.modules.inventory.entity.InventoryTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionSummaryResponseMapper extends BaseMapper<InventoryTransaction, TransactionSummaryResponse> {
    @Override
    @Mapping(source = "employee.username", target = "employeeName")
    @Mapping(source = "warehouse.name", target = "warehouseName")
    TransactionSummaryResponse toDto(InventoryTransaction entity);

    @Override
    List<TransactionSummaryResponse> toDtoList(List<InventoryTransaction> entities);
}
