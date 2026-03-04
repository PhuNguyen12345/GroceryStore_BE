package com.example.localpos.modules.crm.service;

import com.example.localpos.common.exceptions.ResourceNotFoundException;
import com.example.localpos.modules.crm.entity.Customer;
import com.example.localpos.modules.crm.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findByPhone(String phoneRaw) {
        String phone = normalizePhone(phoneRaw);

        return customerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    private String normalizePhone(String phone) {
        return phone == null ? null : phone.trim().replaceAll("\\s+", "");
    }
}