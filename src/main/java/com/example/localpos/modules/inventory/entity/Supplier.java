package com.example.localpos.modules.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @OneToMany(mappedBy = "supplier")
    private Set<InventoryBatch> inventoryBatches = new LinkedHashSet<>();

}