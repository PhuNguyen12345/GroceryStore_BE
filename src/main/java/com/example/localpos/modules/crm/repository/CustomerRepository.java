package com.example.localpos.modules.crm.repository;

import com.example.localpos.enums.CustomerTier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.localpos.modules.crm.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhone(String phone);
    Page<Customer> findAllByIsActive(Boolean isActive, Pageable pageable);

    Page<Customer> findAllByCustomerTier(CustomerTier tier, Pageable pageable);

    Page<Customer> findAllByFullNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Customer> findAllByPhoneContaining(String keyword, Pageable pageable);

    Page<Customer> findAllByEmailContainingIgnoreCase(String keyword, Pageable pageable);

}
