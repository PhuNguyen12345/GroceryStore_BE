package com.example.localpos.modules.pos.dto.request;

import com.example.localpos.enums.PaymentMethod;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class CheckoutRequest {
    private PaymentMethod paymentMethod;
    private Long voucherId;
    private Integer usedPoints;
    private BigDecimal amountPaid;
}
