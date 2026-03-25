package com.example.localpos.modules.pos.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BankWebhookRequest {
    private List<BankTransactionRequest> transactions = new ArrayList<>();
}
