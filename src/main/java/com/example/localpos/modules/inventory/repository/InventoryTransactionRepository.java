package com.example.localpos.modules.inventory.repository;

import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.inventory.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction,Long>,
        JpaSpecificationExecutor<InventoryTransaction> {

    @Query("SELECT b from InventoryBatch b " +
            "WHERE b.warehouse.id = :warehouseId " +
            "AND b.productUnit.id = :productUnitId " +
            "AND b.quantityAvailable > 0 " +
            "ORDER BY b.expiryDate ASC, b.createdAt ASC")
    List<InventoryBatch> findAvailableBatchesForExport(
            @Param("warehouseId") Long warehouseId,
            @Param("productUnitId") Long productUnitId
    );
}
