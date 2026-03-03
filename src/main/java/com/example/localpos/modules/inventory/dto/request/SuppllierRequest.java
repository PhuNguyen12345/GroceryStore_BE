package com.example.localpos.modules.inventory.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuppllierRequest {
    private Long id;
    private String name;
    private String contactPerson;
    private String phone;
    private String address;
}
