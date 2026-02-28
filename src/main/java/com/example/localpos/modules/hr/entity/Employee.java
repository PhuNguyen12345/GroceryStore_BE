package com.example.localpos.modules.hr.entity;

import com.example.localpos.enums.EmployeeRole;
import com.example.localpos.modules.inventory.entity.InventoryTransaction;
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
@Table(name = "employees", uniqueConstraints = {
        @UniqueConstraint(name = "username", columnNames = {"username"}),
        @UniqueConstraint(name = "email", columnNames = {"email"})
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @ColumnDefault("'CASHIER'")
    @Column(name = "role", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

    @ColumnDefault("1")
    @Column(name = "is_active")
    private Boolean isActive;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "employee")
    private Set<InventoryTransaction> inventoryTransactions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<WorkSchedule> workSchedules = new LinkedHashSet<>();

}