package com.example.localpos.modules.inventory.service.impl;

import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import com.example.localpos.modules.inventory.dto.transaction.exports.request.ExportReceiptRequest;
import com.example.localpos.modules.inventory.dto.transaction.imports.response.ImportReceiptResponse;
import com.example.localpos.modules.inventory.entity.InventoryTransaction;
import com.example.localpos.modules.inventory.entity.Warehouse;
import com.example.localpos.modules.inventory.repository.InventoryBatchRepository;
import com.example.localpos.modules.inventory.repository.InventoryTransactionRepository;
import com.example.localpos.modules.inventory.repository.TransactionDetailRepository;
import com.example.localpos.modules.inventory.repository.WarehouseRepository;
import com.example.localpos.modules.inventory.service.InventoryExportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryExportServiceImpl implements InventoryExportService {

    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final InventoryBatchRepository  inventoryBatchRepository;
    private final WarehouseRepository warehouseRepository;
    private final EmployeeRepository  employeeRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ImportReceiptResponse exportGoods(ExportReceiptRequest request) {
        //find warehouse by id
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId()).orElseThrow(
                () -> new RuntimeException("Không tìm thấy nhà kho tương ứng.")
        );
        //find emp by id
        Employee  employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(
                () -> new RuntimeException("Không tìm thấy nhân viên tương ứng.")
        );
        //Create EXPORT transaction ticket
        InventoryTransaction transaction = InventoryTransaction

        return null;
    }
}
