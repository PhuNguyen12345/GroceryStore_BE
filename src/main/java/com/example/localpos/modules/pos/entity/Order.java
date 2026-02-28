package com.example.localpos.modules.pos.entity;

import com.example.localpos.modules.crm.entity.Customer;
import com.example.localpos.modules.crm.entity.Voucher;
import com.example.localpos.modules.hr.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_order_status", columnList = "status")
}, uniqueConstraints = {
        @UniqueConstraint(name = "order_code", columnNames = {"order_code"})
})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_code", nullable = false, length = 50)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @ColumnDefault("0.00")
    @Column(name = "discount_amount", precision = 15, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "final_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal finalAmount;

    @ColumnDefault("'CASH'")
    @Lob
    @Column(name = "payment_method")
    private String paymentMethod;

    @ColumnDefault("'COMPLETED'")
    @Lob
    @Column(name = "status")
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @Column(name = "voucher_code", length = 50)
    private String voucherCode;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "order")
    private Set<Payment> payments = new LinkedHashSet<>();

}