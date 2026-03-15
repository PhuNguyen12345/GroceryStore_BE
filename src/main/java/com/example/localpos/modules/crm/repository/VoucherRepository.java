package com.example.localpos.modules.crm.repository;

import com.example.localpos.enums.DiscountType;
import com.example.localpos.modules.crm.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    Optional<Voucher> findByCode(String code);

    Page<Voucher> findAllByIsActive(Boolean isActive, Pageable pageable);

    Page<Voucher> findAllByDiscountType(DiscountType discountType, Pageable pageable);

    Page<Voucher> findAllByCodeContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Voucher> findAllByDescriptionContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("""
            SELECT v
            FROM Voucher v
            WHERE v.isActive = true
              AND (v.startDate IS NULL OR v.startDate <= :currentTime)
              AND (v.endDate IS NULL OR v.endDate >= :currentTime)
              AND (v.quantityLimit IS NULL OR COALESCE(v.quantityUsed, 0) < v.quantityLimit)
              AND (v.minOrderValue IS NULL OR v.minOrderValue <= :orderValue)
            """)
    Page<Voucher> findApplicableVouchers(
            @Param("orderValue") BigDecimal orderValue,
            @Param("currentTime") Instant currentTime,
            Pageable pageable
    );
}
