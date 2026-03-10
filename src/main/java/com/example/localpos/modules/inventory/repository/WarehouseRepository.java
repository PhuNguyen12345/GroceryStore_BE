package com.example.localpos.modules.inventory.repository;

import com.example.localpos.modules.inventory.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Page<Warehouse> findAll(Pageable pageable);

    Page<Warehouse> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
