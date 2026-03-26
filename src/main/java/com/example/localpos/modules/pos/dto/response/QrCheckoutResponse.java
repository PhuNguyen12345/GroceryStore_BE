package com.example.localpos.modules.pos.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class QrCheckoutResponse {
    private Long orderId;
    private String orderCode;
    private Long providerOrderCode;
    private BigDecimal amount;
    private String qrUrl;
    private String qrCode;
    private String checkoutUrl;
    private String provider;
    private Long payOsOrderCode;
}
