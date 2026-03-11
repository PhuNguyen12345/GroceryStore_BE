package com.example.localpos.modules.inventory.service.impl;

import com.example.localpos.enums.InventoryTransactionType;
import com.example.localpos.modules.hr.entity.Employee;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import com.example.localpos.modules.inventory.dto.transaction.imports.request.ImportItemRequest;
import com.example.localpos.modules.inventory.dto.transaction.imports.request.ImportReceiptRequest;
import com.example.localpos.modules.inventory.dto.transaction.imports.response.ImportReceiptResponse;
import com.example.localpos.modules.inventory.entity.*;
import com.example.localpos.modules.inventory.repository.*;
import com.example.localpos.modules.inventory.service.InventoryImportService;
import com.example.localpos.modules.product.entity.ProductUnit;
import com.example.localpos.modules.product.repository.ProductUnitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InventoryImportServiceImpl implements InventoryImportService {
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final InventoryBatchRepository inventoryBatchRepository;
    private final SupplierRepository supplierRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final WarehouseRepository warehouseRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductUnitRepository productUnitRepository;

    @Override
    public ImportReceiptResponse importGoods(ImportReceiptRequest request) {
        //Check master data
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId()).orElseThrow(()
                -> new RuntimeException(("Không tìm thấy kho với id: "+request.getWarehouseId())));
        Supplier supplier = supplierRepository.findById(request.getSupplierId()).orElseThrow(()
                -> new RuntimeException("Không tìm thấy nhà cung cấp với id : "+request.getSupplierId()));
        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(()
                -> new RuntimeException(("Không tìm thấy nhân viên có id : "+request.getEmployeeId())));

        //Create inventory transaction - import tickets
        InventoryTransaction transaction = new InventoryTransaction();
        //set items
        transaction.setTransactionType(InventoryTransactionType.IMPORT);
        //set employee
        transaction.setEmployee(employee);
        //set note
        transaction.setNote(request.getNote());

        //Save into db to get id for detail
        InventoryTransaction savedTransaction = inventoryTransactionRepository.save(transaction);

        List<TransactionDetail> transactionDetails = new ArrayList<>();

        //Iterate through each item in the request
        for (ImportItemRequest itemRequest : request.getItems()) {
            //Find Product Unit (can trace back to product by id)
            ProductUnit unit = productUnitRepository.findById(itemRequest.getProductUnitId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn vị sản phẩm hợp lệ."));
            //Create batch
            InventoryBatch batch = new InventoryBatch();
            //set unit
            batch.setProductUnit(unit);
            batch.setSupplier(supplier);
            batch.setWarehouse(warehouse);
            batch.setBatchCode("BATCH-"+ UUID.randomUUID().toString().substring(0, 5));
            batch.setQuantityAvailable(itemRequest.getQuantity());
            batch.setImportPrice(itemRequest.getImportPrice());
            batch.setExpiryDate(itemRequest.getExpiryDate());

            //save batch
            InventoryBatch savedBatch = inventoryBatchRepository.save(batch);
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setTransaction(savedTransaction);
            transactionDetail.setInventoryBatch(savedBatch);
            transactionDetail.setQuantity(itemRequest.getQuantity());
            transactionDetails.add(transactionDetail);
        }

        transactionDetailRepository.saveAll(transactionDetails);
        return ImportReceiptResponse.builder()
                .transactionId(savedTransaction.getId())
                .message("Nhập kho thành công").build();
    }
}
