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
    public static Specification<InventoryBatch> filterBatches(
            String batchCode,
            String productName,
            String warehouseName,
            String supplierName,
            LocalDate fromExpiryDate,
            LocalDate toExpiryDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //:batchCode is NULL OR LOWER(b.batchCode) LIKE LOWER(CONCAT('%',:batchCode,'%')))
            //1. Filter for batch code
            if (StringUtils.hasText(batchCode)) {
                predicates.add(cb.like(cb.lower(root.get("batchCode")), "%" + batchCode.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(productName)) {
                //InventoryBatch b INNER JOIN ProductUnit
                Join<Object, Object> productUnitJoin = root.join("productUnit", JoinType.INNER);
                //ProductUnit pu INNER JOIN Product p
                Join<Object, Object> productJoin = productUnitJoin.join("product", JoinType.INNER);
                predicates.add(cb.like(cb.lower(productJoin.get("name")), "%" + productName.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(warehouseName)) {
                Join<Object, Object> warehouseJoin = root.join("warehouse", JoinType.INNER);
                predicates.add(cb.like(cb.lower(warehouseJoin.get("name")), "%" + warehouseName.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(supplierName)) {
                Join<Object, Object > supplierJoin = root.join("supplier", JoinType.INNER);
                predicates.add(cb.like(cb.lower(supplierJoin.get("name")), "%" + supplierName.toLowerCase() + "%"));
            }

            //between fromExpiryDate and toExpiryDate
            if (fromExpiryDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("expiryDate"), fromExpiryDate));
            }
            if (toExpiryDate != null) {
                predicates.add(cb.lessThan(root.get("expiryDate"), toExpiryDate));
            }

            //toArray without param -> Object[]
            //new Predicate[0] ~ new Predicate[predicates.size]
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
