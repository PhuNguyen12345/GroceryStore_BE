package com.example.localpos.modules.crm.service;
import com.example.localpos.modules.crm.entity.Customer;

public interface CustomerService {
    Customer findByPhone(String phoneRaw);
}
