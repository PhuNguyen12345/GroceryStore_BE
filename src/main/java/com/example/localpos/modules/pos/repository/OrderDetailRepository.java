package com.example.localpos.modules.pos.repository;

import com.example.localpos.modules.pos.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder_Id(Long orderId);
}
