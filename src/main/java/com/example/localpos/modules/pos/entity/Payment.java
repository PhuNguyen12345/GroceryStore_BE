package com.example.localpos.modules.pos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_ref", columnList = "transaction_ref")
})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Lob
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "transaction_ref", length = 100)
    private String transactionRef;

    @ColumnDefault("'PENDING'")
    @Lob
    @Column(name = "status")
    private String status;

    @Lob
    @Column(name = "payment_payload")
    private String paymentPayload;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}