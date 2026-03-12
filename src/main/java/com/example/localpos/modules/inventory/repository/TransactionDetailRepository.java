package com.example.localpos.modules.inventory.repository;

import com.example.localpos.modules.inventory.entity.InventoryTransaction;
import com.example.localpos.modules.inventory.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail,Long> {
}
