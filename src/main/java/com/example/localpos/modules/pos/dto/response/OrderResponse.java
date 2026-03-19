package com.example.localpos.modules.pos.dto.response;

import com.example.localpos.enums.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
public class OrderResponse {
    private Long id;
    private String orderCode;
    private String employeeName;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderDetailResponse> orderDetails;
}
