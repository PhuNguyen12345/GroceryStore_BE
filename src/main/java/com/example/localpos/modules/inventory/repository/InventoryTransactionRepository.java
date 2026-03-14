package com.example.localpos.modules.inventory.repository;

import com.example.localpos.modules.inventory.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction,Long>,
        JpaSpecificationExecutor<InventoryTransaction> {
}
