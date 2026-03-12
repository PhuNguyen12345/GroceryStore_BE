package com.example.localpos.modules.product.repository;

import com.example.localpos.modules.product.entity.ProductUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductUnitRepository extends JpaRepository<ProductUnit, Long> {
    List<ProductUnit> findByProduct_Id(Long productId);
}
