package com.example.localpos.modules.crm.service;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.enums.CustomerTier;
import com.example.localpos.modules.crm.dto.request.CustomerCreateRequest;
import com.example.localpos.modules.crm.dto.request.CustomerUpdateRequest;
import com.example.localpos.modules.crm.dto.response.CustomerResponse;
import com.example.localpos.modules.crm.entity.Customer;
import com.example.localpos.modules.crm.mapper.CustomerCreateRequestMapper;
import com.example.localpos.modules.crm.mapper.CustomerResponseMapper;
import com.example.localpos.modules.crm.mapper.CustomerUpdateRequestMapper;
import com.example.localpos.modules.crm.repository.CustomerRepository;
import com.example.localpos.modules.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerCreateRequestMapper createMapper;
    private final CustomerUpdateRequestMapper updateMapper;
    private final CustomerResponseMapper responseMapper;

    @Override
    public CustomerResponse create(CustomerCreateRequest request) {
        if (customerRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Phone already exists");
        }

        Customer customer = createMapper.toEntity(request);

        if (customer.getIsActive() == null) {
            customer.setIsActive(true);
        }

        Customer saved = customerRepository.save(customer);
        return responseMapper.toDto(saved);
    }

    @Override
    public CustomerResponse update(Long id, CustomerUpdateRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (request.getPhone() != null) {
            customerRepository.findByPhone(request.getPhone()).ifPresent(existing -> {
                if (!existing.getId().equals(customer.getId())) {
                    throw new IllegalArgumentException("Phone already exists");
                }
            });
            customer.setPhone(request.getPhone());
        }

        if (request.getFullName() != null) {
            customer.setFullName(request.getFullName());
        }

        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }

        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }

        if (request.getIsActive() != null) {
            customer.setIsActive(request.getIsActive());
        }

        Customer saved = customerRepository.save(customer);
        return responseMapper.toDto(saved);
    }

    @Override
    public CustomerResponse getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return responseMapper.toDto(customer);
    }

    @Override
    public CustomerResponse getByPhone(String phone) {
        Customer customer = customerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return responseMapper.toDto(customer);
    }

    @Override
    public PageResponse<CustomerResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        List<CustomerResponse> responses = customerPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<CustomerResponse>builder()
                .content(responses)
                .page(customerPage.getNumber())
                .size(customerPage.getSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<CustomerResponse> filterByIsActive(Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAllByIsActive(isActive, pageable);

        List<CustomerResponse> responses = customerPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<CustomerResponse>builder()
                .content(responses)
                .page(customerPage.getNumber())
                .size(customerPage.getSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<CustomerResponse> filterByTier(CustomerTier tier, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAllByCustomerTier(tier, pageable);

        List<CustomerResponse> responses = customerPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<CustomerResponse>builder()
                .content(responses)
                .page(customerPage.getNumber())
                .size(customerPage.getSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<CustomerResponse> searchByFullName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAllByFullNameContainingIgnoreCase(keyword, pageable);

        List<CustomerResponse> responses = customerPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<CustomerResponse>builder()
                .content(responses)
                .page(customerPage.getNumber())
                .size(customerPage.getSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<CustomerResponse> searchByPhone(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAllByPhoneContaining(keyword, pageable);

        List<CustomerResponse> responses = customerPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<CustomerResponse>builder()
                .content(responses)
                .page(customerPage.getNumber())
                .size(customerPage.getSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<CustomerResponse> searchByEmail(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAllByEmailContainingIgnoreCase(keyword, pageable);

        List<CustomerResponse> responses = customerPage.getContent()
                .stream()
                .map(responseMapper::toDto)
                .toList();

        return PageResponse.<CustomerResponse>builder()
                .content(responses)
                .page(customerPage.getNumber())
                .size(customerPage.getSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .build();
    }

    @Override
    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (Boolean.FALSE.equals(customer.getIsActive())) {
            return;
        }

        customer.setIsActive(false);
        customerRepository.save(customer);
    }

    @Override
    public void restore(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setIsActive(true);
        customerRepository.save(customer);
    }
}