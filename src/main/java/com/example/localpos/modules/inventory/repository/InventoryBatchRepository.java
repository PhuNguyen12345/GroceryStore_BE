package com.example.localpos.modules.inventory.repository;

import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.product.entity.ProductUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryBatchRepository extends JpaRepository<InventoryBatch,Long>,
        JpaSpecificationExecutor<InventoryBatch> {
    List<InventoryBatch> findByProductUnitAndQuantityAvailableGreaterThanOrderByCreatedAtAsc(
            ProductUnit productUnit,
            Integer quantity
    );
}
