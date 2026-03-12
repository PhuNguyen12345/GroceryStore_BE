package com.example.localpos.modules.inventory.repository.specification;

import com.example.localpos.modules.inventory.entity.InventoryBatch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BatchSpecification {
    public static Specification filterBatches(
            String batchCode,
            String productName,
            String warehouseName,
            String supplierName,
            LocalDate fromExpiryDate,
            LocalDate toExpiryDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            //1. Filter for batch code
            if (StringUtils.hasText(batchCode)) {
                predicates.add(cb.like(cb.lower(root.get("batchCode")), "%" + batchCode.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(productName)) {
                Join<Object, Object> productUnitJoin = root.join("productUnit", JoinType.INNER);
                Join<Object, Object> productJoin = productUnitJoin.join("product", JoinType.INNER);
                predicates.add(cb.like(cb.lower(productJoin.get("name")), "%" + productName.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(warehouseName)) {
                Join<Object, Object> warehouseJoin = root.join("warehouse", JoinType.INNER);

            }
        }
    }
}
