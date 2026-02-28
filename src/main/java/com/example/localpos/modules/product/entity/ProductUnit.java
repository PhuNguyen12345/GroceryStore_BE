package com.example.localpos.modules.product.entity;

import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.pos.entity.OrderDetail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product_units", uniqueConstraints = {
        @UniqueConstraint(name = "barcode", columnNames = {"barcode"})
})
public class ProductUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "unit_name", nullable = false, length = 50)
    private String unitName;

    @ColumnDefault("1")
    @Column(name = "conversion_factor")
    private Integer conversionFactor;

    @Column(name = "barcode", length = 50)
    private String barcode;

    @Column(name = "selling_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal sellingPrice;

    @ColumnDefault("10")
    @Column(name = "reorder_level")
    private Integer reorderLevel;

    @ColumnDefault("0")
    @Column(name = "is_base_unit")
    private Boolean isBaseUnit;

    @OneToMany(mappedBy = "productUnit")
    private Set<InventoryBatch> inventoryBatches = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productUnit")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

}