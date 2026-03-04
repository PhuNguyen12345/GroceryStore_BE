package com.example.localpos.modules.crm.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.modules.crm.dto.request.FindCustomerByPhoneRequest;
import com.example.localpos.modules.crm.dto.response.CustomerResponse;
import com.example.localpos.modules.crm.entity.Customer;
import com.example.localpos.modules.crm.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.CRMCtrl.CUSTOMER)
public class CrmController {

    private final CustomerService customerService;

    public CrmController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/lookup")
    public ResponseEntity<CustomerResponse> findCustomerByPhone(
            @Valid @RequestBody FindCustomerByPhoneRequest req
    ) {
        Customer c = customerService.findByPhone(req.getPhone());

        CustomerResponse res = new CustomerResponse();
        res.setId(c.getId());
        res.setPhone(c.getPhone());
        res.setFullName(c.getFullName());
        res.setEmail(c.getEmail());
        res.setAddress(c.getAddress());
        res.setLoyaltyPoints(c.getLoyaltyPoints());
        res.setCustomerTier(c.getCustomerTier());
        res.setIsActive(c.getIsActive());

        return ResponseEntity.ok(res);
    }
}