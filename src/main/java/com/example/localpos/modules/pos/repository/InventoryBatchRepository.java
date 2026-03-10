package com.example.localpos.modules.pos.repository;

import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.product.entity.ProductUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Long> {
    List<InventoryBatch> findByProductUnitAndQuantityAvailableGreaterThanOrderByCreatedAtAsc(
            ProductUnit productUnit,
            Integer quantity
    );
}
