package com.example.localpos.modules.pos.repository;

import com.example.localpos.modules.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
