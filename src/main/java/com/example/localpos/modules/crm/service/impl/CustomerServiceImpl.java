package com.example.localpos.modules.crm.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.enums.CustomerTier;
import com.example.localpos.modules.crm.dto.request.CustomerRequest;
import com.example.localpos.modules.crm.dto.response.CustomerResponse;
import com.example.localpos.modules.crm.entity.Customer;
import com.example.localpos.modules.crm.mapper.CustomerResponseMapper;
import com.example.localpos.modules.crm.repository.CustomerRepository;
import com.example.localpos.modules.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerResponseMapper responseMapper;

    @Override
    public CustomerResponse saveCustomer(CustomerRequest requestBody) {
        // Validate request body.
        if (ObjectUtils.isEmpty(requestBody)) {
            throw new IllegalArgumentException("Dữ liệu yêu cầu đang trống");
        }

        Customer customer;

        if (!ObjectUtils.isEmpty(requestBody.getId())) {
            // Update existing customer.
            customer = customerRepository.findById(requestBody.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khách hàng cần cập nhật"));

            // Reject phone number if it belongs to another customer.
            customerRepository.findByPhone(requestBody.getPhone())
                    .ifPresent(existingCustomer -> {
                        if (!existingCustomer.getId().equals(customer.getId())) {
                            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
                        }
                    });
        } else {
            // Create new customer with unique phone number.
            if (customerRepository.findByPhone(requestBody.getPhone()).isPresent()) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại");
            }
            customer = new Customer();
        }

        // Copy editable fields from request into entity.
        BeanUtils.copyProperties(requestBody, customer, "id");

        // Default customer status to active when missing.
        if (ObjectUtils.isEmpty(customer.getIsActive())) {
            customer.setIsActive(true);
        }

        Customer saved = customerRepository.save(customer);
        return responseMapper.toDto(saved);
    }

    @Override
    public CustomerResponse getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        return responseMapper.toDto(customer);
    }

    @Override
    public CustomerResponse getByPhone(String phone) {
        Customer customer = customerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

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
        // Return all customers when no active filter is provided.
        Page<Customer> customerPage = (isActive == null)
                ? customerRepository.findAll(pageable)
                : customerRepository.findAllByIsActive(isActive, pageable);

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
        // Fallback to full list when search keyword is empty.
        Page<Customer> customerPage = (keyword == null || keyword.trim().isBlank())
                ? customerRepository.findAll(pageable)
                : customerRepository.findAllByFullNameContainingIgnoreCase(keyword.trim(), pageable);

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
        // Fallback to full list when search keyword is empty.
        Page<Customer> customerPage = (keyword == null || keyword.trim().isBlank())
                ? customerRepository.findAll(pageable)
                : customerRepository.findAllByPhoneContaining(keyword.trim(), pageable);

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
        // Fallback to full list when search keyword is empty.
        Page<Customer> customerPage = (keyword == null || keyword.trim().isBlank())
                ? customerRepository.findAll(pageable)
                : customerRepository.findAllByEmailContainingIgnoreCase(keyword.trim(), pageable);

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
    public PageResponse<CustomerResponse> searchCustomers(
            String keyword,
            Boolean isActive,
            CustomerTier tier,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        // Combine search keyword and filters in a single query.
        Page<Customer> customerPage = customerRepository.searchCustomers(
                keyword == null ? null : keyword.trim(),
                isActive,
                tier,
                pageable
        );

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
        // Soft delete by switching active status off.
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        if (Boolean.FALSE.equals(customer.getIsActive())) {
            return;
        }

        customer.setIsActive(false);
        customerRepository.save(customer);
    }

    @Override
    public void restore(Long id) {
        // Restore customer by switching active status on.
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        customer.setIsActive(true);
        customerRepository.save(customer);
    }
}
