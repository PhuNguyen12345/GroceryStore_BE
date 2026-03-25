package com.example.localpos.modules.pos.repository;

import com.example.localpos.enums.PaymentMethod;
import com.example.localpos.enums.PaymentStatus;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrder(Order order);

    Optional<Payment> findTopByOrder_IdAndPaymentMethodAndStatusOrderByCreatedAtDesc(
            Long orderId,
            PaymentMethod paymentMethod,
            PaymentStatus status
    );

    boolean existsByTransactionRef(String transactionRef);

    List<Payment> findTop200ByStatusAndPaymentMethodOrderByCreatedAtAsc(
            PaymentStatus status,
            PaymentMethod paymentMethod
    );
}
