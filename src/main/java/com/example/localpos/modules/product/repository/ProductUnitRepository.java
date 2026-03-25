package com.example.localpos.modules.product.repository;

import com.example.localpos.modules.product.entity.ProductUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductUnitRepository extends JpaRepository<ProductUnit, Long> {
    Page<ProductUnit> findByProduct_Id(Long productId, Pageable pageable);
    List<ProductUnit> findByProduct_NameContainingIgnoreCase(String productName);
}
