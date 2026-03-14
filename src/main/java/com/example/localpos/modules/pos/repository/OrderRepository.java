package com.example.localpos.modules.pos.repository;

import com.example.localpos.modules.pos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Tìm kiếm nhanh theo mã đơn hàng
    Optional<Order> findByOrderCode(String orderCode);
    // Kiểm tra mã đơn hàng đã tồn tại chưa để tránh lỗi
    boolean existsByOrderCode(String orderCode);
}
