package com.example.localpos.modules.inventory.repository;

import com.example.localpos.modules.inventory.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    Page<Supplier> findAll(Pageable pageable);
}
