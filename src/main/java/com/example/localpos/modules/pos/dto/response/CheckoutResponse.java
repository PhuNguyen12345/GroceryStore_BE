package com.example.localpos.modules.pos.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CheckoutResponse {

    private BigDecimal total;
    private BigDecimal finalTotal;

    private Integer usedPoints;
    private Integer earnedPoints;

    private QrResponse qr;

    private String status;
    private String message;

}
