package com.example.localpos.modules.crm.dto.response;

import com.example.localpos.enums.CustomerTier;
import lombok.Getter;   
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    private Long id;
    private String phone;
    private String fullName;
    private String email;
    private String address;
    private Integer loyaltyPoints;
    private CustomerTier customerTier;
    private Boolean isActive;
}
