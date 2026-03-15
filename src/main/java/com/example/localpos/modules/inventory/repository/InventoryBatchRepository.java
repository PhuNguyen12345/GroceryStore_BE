package com.example.localpos.modules.inventory.repository;

import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.product.entity.ProductUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryBatchRepository extends JpaRepository<InventoryBatch,Long>,
        JpaSpecificationExecutor<InventoryBatch> {
    List<InventoryBatch> findByProductUnitAndQuantityAvailableGreaterThanOrderByCreatedAtAsc(
            ProductUnit productUnit,
            Integer quantity
    );
    //query batches by expiry date ascending to do fi-fo export
    @Query("SELECT b from InventoryBatch b " +
            "WHERE b.warehouse.id = :warehouseId " +
            "AND b.productUnit.id = :productUnitId " +
            "AND b.quantityAvailable > 0 " +
            "ORDER BY b.expiryDate ASC, b.createdAt ASC")
    List<InventoryBatch> findAvailableBatchesForExport(
            @Param("warehouseId") Long warehouseId,
            @Param("productUnitId") Long productUnitId
    );

    // Calculate sum of available quantity of a unit in a warehouse
    //Tổng tồn kho 1 đơn vị sản phẩm trong 1 kho.
    @Query("SELECT COALESCE(SUM(b.quantityAvailable), 0) FROM InventoryBatch b " +
            "WHERE b.warehouse.id = :warehouseId AND b.productUnit.id = :productUnitId")
    Integer getTotalAvailableStock(@Param("warehouseId") Long warehouseId,
                                   @Param("productUnitId") Long productUnitId);
}
