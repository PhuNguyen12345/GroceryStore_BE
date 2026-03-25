package com.example.localpos.modules.pos.service.impl;

import com.example.localpos.enums.OrderStatus;
import com.example.localpos.modules.pos.dto.request.BankTransactionRequest;
import com.example.localpos.modules.pos.dto.request.BankWebhookRequest;
import com.example.localpos.modules.pos.dto.request.PayOsWebhookRequest;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.repository.OrderRepository;
import com.example.localpos.modules.pos.service.BankReconciliationService;
import com.example.localpos.modules.pos.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BankReconciliationServiceImpl implements BankReconciliationService {
    private static final Pattern ORDER_CODE_PATTERN = Pattern.compile("ORD-[A-Z0-9-]+");

    @Value("${app.payment.bank-provider.enabled:false}")
    private boolean providerEnabled;

    @Value("${app.payment.bank-provider.url:}")
    private String providerUrl;

    @Value("${app.payment.bank-provider.token:}")
    private String providerToken;

    @Value("${app.payment.payos.enabled:false}")
    private boolean payOsEnabled;

    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @Transactional
    public int processIncomingTransactions(List<BankTransactionRequest> transactions) {
        if (transactions == null || transactions.isEmpty()) return 0;

        int matched = 0;
        for (BankTransactionRequest tx : transactions) {
            if (tx == null) continue;

            String orderCode = extractOrderCode(tx.getDescription());
            if (orderCode.isEmpty()) continue;

            Order order = orderRepository.findByOrderCode(orderCode).orElse(null);
            if (order == null || order.getStatus() != OrderStatus.PENDING) continue;

            BigDecimal amount = tx.getAmount();
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) continue;

            String rawPayload = safeText(tx.getRawPayload());
            String transactionRef = buildTransactionRef(tx, orderCode, amount, rawPayload);
            orderService.confirmQrPayment(order.getId(), amount, transactionRef, rawPayload);
            matched += 1;
        }

        return matched;
    }

    @Override
    @Transactional
    public int processPayOsWebhook(PayOsWebhookRequest request) {
        if (!payOsEnabled || request == null || request.getData() == null) {
            return 0;
        }

        Boolean success = request.getSuccess();
        if (success != null && !success) {
            return 0;
        }

        PayOsWebhookRequest.PayOsWebhookData data = request.getData();
        BigDecimal amount = data.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }

        String transactionRef = safeText(data.getReference());
        String payOsOrderCode = safeText(data.getOrderCode());
        if (transactionRef.isEmpty() && !payOsOrderCode.isEmpty()) {
            transactionRef = "PAYOS-" + payOsOrderCode;
        }

        Order matchedOrder = findOrderFromPayOsPayload(payOsOrderCode, data.getDescription(), amount);
        if (matchedOrder != null && matchedOrder.getStatus() == OrderStatus.PENDING) {
            orderService.confirmQrPayment(matchedOrder.getId(), amount, transactionRef, toJsonSafely(request));
            return 1;
        }

        BankTransactionRequest tx = new BankTransactionRequest();
        tx.setTransactionRef(transactionRef);
        tx.setAmount(amount);
        tx.setDescription(data.getDescription());
        tx.setRawPayload(toJsonSafely(request));
        return processIncomingTransactions(List.of(tx));
    }

    @Override
    public int pullAndProcessFromProvider() {
        if (!providerEnabled || safeText(providerUrl).isEmpty()) return 0;

        try {
            HttpHeaders headers = new HttpHeaders();
            if (!safeText(providerToken).isEmpty()) {
                headers.setBearerAuth(providerToken.trim());
            }

            ResponseEntity<BankWebhookRequest> response = restTemplate.exchange(
                    providerUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    BankWebhookRequest.class
            );

            BankWebhookRequest body = response.getBody();
            return processIncomingTransactions(body != null ? body.getTransactions() : List.of());
        } catch (Exception ignored) {
            return 0;
        }
    }

    @Scheduled(fixedDelayString = "${app.payment.bank-provider.poll-ms:15000}")
    public void scheduledPull() {
        pullAndProcessFromProvider();
    }

    private String extractOrderCode(String description) {
        String source = safeText(description).toUpperCase(Locale.ROOT);
        Matcher matcher = ORDER_CODE_PATTERN.matcher(source);
        return matcher.find() ? matcher.group() : "";
    }

    private String safeText(String value) {
        return value == null ? "" : value.trim();
    }

    private String toJsonSafely(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ignored) {
            return "";
        }
    }

    private Order findOrderFromPayOsPayload(String payOsOrderCode, String description, BigDecimal amount) {
        Long orderId = parseLongSafely(payOsOrderCode);
        if (orderId != null) {
            Order byId = orderRepository.findById(orderId).orElse(null);
            if (byId != null) {
                return byId;
            }
        }

        String orderCodeFromPayOs = extractOrderCode(payOsOrderCode);
        if (!orderCodeFromPayOs.isEmpty()) {
            Order byCode = orderRepository.findByOrderCode(orderCodeFromPayOs).orElse(null);
            if (byCode != null) {
                return byCode;
            }
        }

        String orderCodeFromDesc = extractOrderCode(description);
        if (!orderCodeFromDesc.isEmpty()) {
            Order byCode = orderRepository.findByOrderCode(orderCodeFromDesc).orElse(null);
            if (byCode != null) {
                return byCode;
            }
        }

        // Last-resort fallback: only accept when exactly one pending order has this amount.
        List<Order> pendingByAmount =
                orderRepository.findByStatusAndFinalAmountOrderByCreatedAtDesc(OrderStatus.PENDING, amount);
        return pendingByAmount.size() == 1 ? pendingByAmount.get(0) : null;
    }

    private Long parseLongSafely(String value) {
        String text = safeText(value);
        if (text.isEmpty()) return null;
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private String buildTransactionRef(
            BankTransactionRequest tx,
            String orderCode,
            BigDecimal amount,
            String rawPayload
    ) {
        String directRef = safeText(tx.getTransactionRef());
        if (!directRef.isEmpty()) {
            return directRef;
        }

        long txEpochMillis = tx.getTransactionTime() != null
                ? tx.getTransactionTime().toEpochMilli()
                : Instant.now().toEpochMilli();

        int payloadHash = rawPayload.isEmpty() ? 0 : Math.abs(rawPayload.hashCode());
        String amountText = amount.stripTrailingZeros().toPlainString();

        return String.format("AUTO-%s-%s-%d-%d", orderCode, amountText, txEpochMillis, payloadHash);
    }
}
