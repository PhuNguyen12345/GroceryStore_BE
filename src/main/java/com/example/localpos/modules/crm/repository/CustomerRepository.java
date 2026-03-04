package com.example.localpos.modules.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.localpos.modules.crm.entity.Customer;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhone(String phone);
}
