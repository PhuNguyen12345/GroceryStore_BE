package com.example.localpos.modules.crm.entity;

import com.example.localpos.enums.DiscountType;
import com.example.localpos.modules.pos.entity.Order;
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
@Table(name = "vouchers", uniqueConstraints = {
        @UniqueConstraint(name = "code", columnNames = {"code"})
})
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @ColumnDefault("100")
    @Column(name = "quantity_limit")
    private Integer quantityLimit;

    @ColumnDefault("0")
    @Column(name = "quantity_used")
    private Integer quantityUsed;

    @ColumnDefault("0.00")
    @Column(name = "min_order_value", precision = 15, scale = 2)
    private BigDecimal minOrderValue;

    @Column(name = "description")
    private String description;

    @Column(name = "discount_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType = DiscountType.FIXED_AMOUNT;

    @Column(name = "discount_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @ColumnDefault("1")
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "voucher")
    private Set<Order> orders = new LinkedHashSet<>();

}