package com.example.localpos.modules.crm.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.enums.CustomerTier;
import com.example.localpos.modules.crm.dto.request.CustomerRequest;
import com.example.localpos.modules.crm.dto.response.CustomerResponse;

public interface CustomerService {

    // create, update
    CustomerResponse saveCustomer(CustomerRequest request);


    // get by id
    CustomerResponse getById(Long id);

    // get by phone
    CustomerResponse getByPhone(String phone);

    // get all (admin)
    PageResponse<CustomerResponse> getAll(int page, int size);

    // filter by active
    PageResponse<CustomerResponse> filterByIsActive(Boolean isActive, int page, int size);

    // filter by tier
    PageResponse<CustomerResponse> filterByTier(CustomerTier tier, int page, int size);

    // search
    PageResponse<CustomerResponse> searchByFullName(String keyword, int page, int size);

    PageResponse<CustomerResponse> searchByPhone(String keyword, int page, int size);

    PageResponse<CustomerResponse> searchByEmail(String keyword, int page, int size);

    // search with filters
    PageResponse<CustomerResponse> searchCustomers(
            String keyword,
            Boolean isActive,
            CustomerTier tier,
            int page,
            int size
    );

    // soft delete
    void delete(Long id);

    // restore
    void restore(Long id);

}
