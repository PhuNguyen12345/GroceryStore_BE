package com.example.localpos.modules.crm.repository;

import com.example.localpos.modules.crm.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    Page<Promotion> findAllByIsActive(Boolean isActive, Pageable pageable);

    Page<Promotion> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);
}