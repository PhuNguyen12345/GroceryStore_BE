package com.example.localpos.modules.pos.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderRequest {
    private Long employeeId;
    private Long customerId;
}
