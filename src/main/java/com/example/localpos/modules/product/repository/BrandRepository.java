package com.example.localpos.modules.product.repository;

import com.example.localpos.modules.product.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    // Lấy toàn bộ brand có phân trang + sort theo name ASC
    Page<Brand> findAllByOrderByNameAsc(Pageable pageable);

    // Search brand theo name (không phân biệt hoa thường)
    Page<Brand> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Lấy brand active (phục vụ client)
    Page<Brand> findByIsActiveTrue(Pageable pageable);

    // check trùng tên (để validate create/update)
    boolean existsByNameIgnoreCase(String name);
}
