package com.example.localpos.modules.pos.repository;

import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrder(Order order);
}
