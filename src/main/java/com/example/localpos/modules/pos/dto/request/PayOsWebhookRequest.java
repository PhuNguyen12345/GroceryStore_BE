package com.example.localpos.modules.pos.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PayOsWebhookRequest {
    private String code;
    private String desc;
    private Boolean success;
    private String signature;
    private PayOsWebhookData data;

    @Getter
    @Setter
    public static class PayOsWebhookData {
        private String orderCode;
        private BigDecimal amount;
        private String description;
        private String reference;
        private String transactionDateTime;
    }
}
