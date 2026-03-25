package com.example.localpos.modules.inventory.repository.specification;

import com.example.localpos.modules.inventory.entity.InventoryTransaction;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {
    public static Specification<InventoryTransaction> filterTransactions(
            String transactionType,
            String warehouseName,
            String employeeName,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            //find by transaction type
            if (StringUtils.hasText(transactionType)) {
                //set predicate for transaction type
                Predicate p = cb.equal(cb.upper(root.get("transactionType")), transactionType.toUpperCase());
                //add to predicate list
                predicates.add(p);
            }
            //find by warehouse
            if (StringUtils.hasText(warehouseName)) {
                //join first
                Join<Object, Object> warehouseJoin = root.join("warehouse", JoinType.INNER);
                //initate predicate
                Predicate p = cb.like(cb.lower(warehouseJoin.get("name")), "%" + warehouseName.toLowerCase() + "%");
                //add to predicate list
                predicates.add(p);
            }
            //find by employee
            if (StringUtils.hasText(employeeName)) {
                Join<Object, Object> employeeJoin = root.join("employee", JoinType.INNER);
                Predicate p = cb.like(cb.lower(employeeJoin.get("username")), "%" + employeeName.toLowerCase() + "%");
                predicates.add(p);
            }
            //find by createdAt
            if (fromDate != null) {
                Instant fromInstant = fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
                Predicate p = cb.greaterThanOrEqualTo(root.get("createdAt"), fromInstant);
                predicates.add(p);
            }

            if (toDate != null) {
                Instant toInstant =  toDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
                Predicate p = cb.lessThanOrEqualTo(root.get("createdAt"), toInstant);
                predicates.add(p);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    //get details from transaction
    public static Specification<InventoryTransaction> hasIdWithDetails(Long id) {
        return (root, query, cb) -> {
            //fetch only if there is a count query called
            if (Long.class != query.getResultType()) {
                //fetch employee and warehouse
                root.fetch("employee",  JoinType.LEFT);
                root.fetch("warehouse",  JoinType.LEFT);
                //fetch transaction details (fetch >< join)
                //join for WHERE, fetch for UNION
                var detailFetch = root.fetch("transactionDetails", JoinType.LEFT);
                //from detail, fetch batch and productUnit
                var batchFetch = detailFetch.fetch("inventoryBatch", JoinType.LEFT);
                batchFetch.fetch("productUnit",  JoinType.LEFT);
            }
            return cb.equal(root.get("id"), id); //find by id = :id
        };
    }
}
