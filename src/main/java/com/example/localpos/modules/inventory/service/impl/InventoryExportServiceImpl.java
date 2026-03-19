package com.example.localpos.modules.inventory.service.impl;

import com.example.localpos.enums.InventoryTransactionType;
import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import com.example.localpos.modules.inventory.dto.transaction.exports.request.ExportItemRequest;
import com.example.localpos.modules.inventory.dto.transaction.exports.request.ExportReceiptRequest;
import com.example.localpos.modules.inventory.dto.transaction.imports.response.ImportReceiptResponse;
import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.inventory.entity.InventoryTransaction;
import com.example.localpos.modules.inventory.entity.TransactionDetail;
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

import java.util.ArrayList;
import java.util.List;

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
        InventoryTransaction transaction = new InventoryTransaction();
        transaction.setTransactionType(InventoryTransactionType.EXPORT);
        transaction.setWarehouse(warehouse);
        transaction.setEmployee(employee);
        transaction.setNote(request.getNotes());

        //save transaction ticket
        InventoryTransaction savedTransaction = inventoryTransactionRepository.save(transaction);

        //list of details
        List<TransactionDetail> details = new ArrayList<>();
        //check for each export item
        for (ExportItemRequest itemRequest : request.getItems()) {
            //find remaining qyantity to export
            int remainingQuantityToExport = itemRequest.getQuantity();
            //find all the available batches, sorted
            List<InventoryBatch> availableBatches = inventoryBatchRepository.findAvailableBatchesForExport
                    (warehouse.getId(), itemRequest.getProductUnitId());

            //check if the total available valid for export
            int totalAvailable = availableBatches.stream()
                    .mapToInt(InventoryBatch::getQuantityAvailable)
                    .sum();

            //Check if total available smaller than qty to export
            if (totalAvailable < remainingQuantityToExport) {
                throw new RuntimeException("Không đủ số lượng tồn kho cho đơn vị sản phẩm: "
                        +itemRequest.getProductUnitId()+
                        "\\n Cần: "+remainingQuantityToExport + ", Đang có: "+totalAvailable);
            }

            //fifo (first-expired, first-out)
            for (InventoryBatch batch : availableBatches) {
                //Check if export enough quantity
                if (remainingQuantityToExport == 0) break;
                //Calculate export quantity from this batch
                int quantityToDeduct = Math.min(batch.getQuantityAvailable(), remainingQuantityToExport);
                //update new quantity for batch
                batch.setQuantityAvailable(batch.getQuantityAvailable() - quantityToDeduct);
                //save to db
                inventoryBatchRepository.save(batch);
                //save to details
                TransactionDetail detail = new TransactionDetail();
                detail.setTransaction(transaction);
                detail.setInventoryBatch(batch);
                detail.setQuantity(quantityToDeduct); //deducted quantity
                details.add(detail);

                //decrease the quantity to export
                remainingQuantityToExport -= quantityToDeduct;
            }
        }

        //save all export details
        transactionDetailRepository.saveAll(details);
        //log info
        log.info("Xuất kho thành công Phiếu với ID: {}", savedTransaction.getId());
        return ImportReceiptResponse.builder()
                .transactionId(savedTransaction.getId())
                .message("Xuất kho thành công!")
                .build();
    }
}
