package com.example.localpos.modules.pos.controller;

import com.example.localpos.modules.pos.dto.request.BankWebhookRequest;
import com.example.localpos.modules.pos.dto.request.PayOsWebhookRequest;
import com.example.localpos.modules.pos.service.BankReconciliationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments/bank")
@RequiredArgsConstructor
public class BankWebhookController {
    private static final String DEFAULT_WEBHOOK_SECRET = "change-me";

    @Value("${app.payment.webhook.secret:}")
    private String webhookSecret;

    private final BankReconciliationService bankReconciliationService;
    private final ObjectMapper objectMapper;

    @PostMapping("/webhook")
    public ResponseEntity<Map<String, Object>> receiveWebhook(
            @RequestHeader(value = "X-Webhook-Secret", required = false) String secret,
            @RequestBody(required = false) JsonNode payload
    ) {
        if (isSecretValidationEnabled() && !webhookSecret.equals(String.valueOf(secret))) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid webhook secret"));
        }

        if (payload == null || payload.isNull()) {
            return ResponseEntity.ok(Map.of("matched", 0));
        }

        int matched;
        if (payload.has("data")) {
            PayOsWebhookRequest payOsWebhook = objectMapper.convertValue(payload, PayOsWebhookRequest.class);
            matched = bankReconciliationService.processPayOsWebhook(payOsWebhook);
        } else {
            BankWebhookRequest request = objectMapper.convertValue(payload, BankWebhookRequest.class);
            matched = bankReconciliationService.processIncomingTransactions(
                    request != null ? request.getTransactions() : null
            );
        }
        return ResponseEntity.ok(Map.of("matched", matched));
    }

    @PostMapping("/pull")
    public ResponseEntity<Map<String, Object>> pullNow() {
        int matched = bankReconciliationService.pullAndProcessFromProvider();
        return ResponseEntity.ok(Map.of("matched", matched));
    }

    private boolean isSecretValidationEnabled() {
        String trimmed = webhookSecret == null ? "" : webhookSecret.trim();
        return !trimmed.isBlank() && !DEFAULT_WEBHOOK_SECRET.equals(trimmed);
    }
}
