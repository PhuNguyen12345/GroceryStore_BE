package com.example.localpos.modules.pos.repository;

import com.example.localpos.enums.OrderStatus;
import com.example.localpos.modules.pos.dto.response.OrderAdminResponse;
import com.example.localpos.modules.pos.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(String orderCode);

    boolean existsByOrderCode(String orderCode);

    List<Order> findByStatusAndFinalAmountOrderByCreatedAtDesc(OrderStatus status, BigDecimal finalAmount);

    List<Order> findTop20ByStatusOrderByCreatedAtDesc(OrderStatus status);

    @Query(
            value = """
                    SELECT new com.example.localpos.modules.pos.dto.response.OrderAdminResponse(
                        o.id,
                        o.orderCode,
                        c.fullName,
                        e.fullName,
                        o.totalAmount,
                        o.discountAmount,
                        o.finalAmount,
                        o.paymentMethod,
                        o.status,
                        o.createdAt,
                        COUNT(od.id)
                    )
                    FROM Order o
                    LEFT JOIN o.customer c
                    LEFT JOIN o.employee e
                    LEFT JOIN o.orderDetails od
                    WHERE (:fromTime IS NULL OR o.createdAt >= :fromTime)
                      AND (:toTime IS NULL OR o.createdAt <= :toTime)
                      AND (:status IS NULL OR o.status = :status)
                      AND (:orderCode IS NULL OR :orderCode = '' OR LOWER(o.orderCode) LIKE LOWER(CONCAT('%', :orderCode, '%')))
                    GROUP BY o.id, o.orderCode, c.fullName, e.fullName, o.totalAmount, o.discountAmount, o.finalAmount, o.paymentMethod, o.status, o.createdAt
                    """,
            countQuery = """
                    SELECT COUNT(o.id)
                    FROM Order o
                    WHERE (:fromTime IS NULL OR o.createdAt >= :fromTime)
                      AND (:toTime IS NULL OR o.createdAt <= :toTime)
                      AND (:status IS NULL OR o.status = :status)
                      AND (:orderCode IS NULL OR :orderCode = '' OR LOWER(o.orderCode) LIKE LOWER(CONCAT('%', :orderCode, '%')))
                    """
    )
    Page<OrderAdminResponse> searchOrdersForAdmin(
            @Param("fromTime") Instant fromTime,
            @Param("toTime") Instant toTime,
            @Param("status") OrderStatus status,
            @Param("orderCode") String orderCode,
            Pageable pageable
    );
}
