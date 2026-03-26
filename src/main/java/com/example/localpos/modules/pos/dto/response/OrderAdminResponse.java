package com.example.localpos.modules.pos.dto.response;

import com.example.localpos.enums.OrderStatus;
import com.example.localpos.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAdminResponse {
    private Long id;
    private String orderCode;
    private String customerName;
    private String employeeName;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private Instant createdAt;
    private Long itemCount;
}