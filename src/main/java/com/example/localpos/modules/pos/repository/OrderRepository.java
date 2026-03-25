package com.example.localpos.modules.pos.repository;

import com.example.localpos.enums.OrderStatus;
import com.example.localpos.modules.pos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(String orderCode);

    boolean existsByOrderCode(String orderCode);

    List<Order> findByStatusAndFinalAmountOrderByCreatedAtDesc(OrderStatus status, BigDecimal finalAmount);

    List<Order> findTop20ByStatusOrderByCreatedAtDesc(OrderStatus status);
}
