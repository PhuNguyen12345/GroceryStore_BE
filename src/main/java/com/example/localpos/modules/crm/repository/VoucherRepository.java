package com.example.localpos.modules.crm.repository;

import com.example.localpos.enums.DiscountType;
import com.example.localpos.modules.crm.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    Optional<Voucher> findByCode(String code);

    Page<Voucher> findAllByIsActive(Boolean isActive, Pageable pageable);

    Page<Voucher> findAllByDiscountType(DiscountType discountType, Pageable pageable);

    Page<Voucher> findAllByCodeContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Voucher> findAllByDescriptionContainingIgnoreCase(String keyword, Pageable pageable);
}