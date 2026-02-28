package com.example.localpos.modules.crm.entity;

import com.example.localpos.modules.pos.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(name = "phone", columnNames = {"phone"})
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "email", length = 100)
    private String email;

    @Lob
    @Column(name = "address")
    private String address;

    @ColumnDefault("0")
    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @ColumnDefault("'BRONZE'")
    @Lob
    @Column(name = "customer_tier")
    private String customerTier;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders = new LinkedHashSet<>();

}