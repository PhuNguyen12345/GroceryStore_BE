package com.example.localpos.modules.pos.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class BankTransactionRequest {
    private String transactionRef;
    private BigDecimal amount;
    private String description;
    private Instant transactionTime;
    private String rawPayload;
}
