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
}
