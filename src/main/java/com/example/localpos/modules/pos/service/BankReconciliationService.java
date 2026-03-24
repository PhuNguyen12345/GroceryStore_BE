package com.example.localpos.modules.pos.service;

import com.example.localpos.modules.pos.dto.request.BankTransactionRequest;
import com.example.localpos.modules.pos.dto.request.PayOsWebhookRequest;

import java.util.List;

public interface BankReconciliationService {
    int processIncomingTransactions(List<BankTransactionRequest> transactions);

    int processPayOsWebhook(PayOsWebhookRequest request);

    int pullAndProcessFromProvider();
}
