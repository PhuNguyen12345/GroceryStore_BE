package com.example.localpos.modules.pos.service;

import com.example.localpos.enums.OrderStatus;
import com.example.localpos.enums.PaymentMethod;
import com.example.localpos.enums.PaymentStatus;
import com.example.localpos.modules.crm.repository.CustomerRepository;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.inventory.repository.InventoryBatchRepository;
import com.example.localpos.modules.pos.dto.request.CartItemRequest;
import com.example.localpos.modules.pos.dto.request.CheckoutRequest;
import com.example.localpos.modules.pos.dto.request.OrderRequest;
import com.example.localpos.modules.pos.dto.response.QrCheckoutResponse;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.entity.OrderDetail;
import com.example.localpos.modules.pos.entity.Payment;
import com.example.localpos.modules.pos.repository.OrderDetailRepository;
import com.example.localpos.modules.pos.repository.OrderRepository;
import com.example.localpos.modules.pos.repository.PaymentRepository;
import com.example.localpos.modules.product.entity.ProductUnit;
import com.example.localpos.modules.product.repository.ProductUnitRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Value("${app.payment.qr.bank:970422}")
    private String qrBankCode;

    @Value("${app.payment.qr.account:123456789}")
    private String qrAccountNo;

    @Value("${app.payment.qr.template:compact2}")
    private String qrTemplate;

    @Value("${app.payment.payos.enabled:false}")
    private boolean payOsEnabled;

    @Value("${app.payment.payos.api-url:https://api-merchant.payos.vn}")
    private String payOsApiUrl;

    @Value("${app.payment.payos.client-id:}")
    private String payOsClientId;

    @Value("${app.payment.payos.api-key:}")
    private String payOsApiKey;

    @Value("${app.payment.payos.checksum-key:}")
    private String payOsChecksumKey;

    @Value("${app.payment.payos.return-url:http://localhost:5173/orders}")
    private String payOsReturnUrl;

    @Value("${app.payment.payos.cancel-url:http://localhost:5173/orders}")
    private String payOsCancelUrl;

    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductUnitRepository productUnitRepository;
    private final InventoryBatchRepository inventoryBatchRepository;
    private final PaymentRepository paymentRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @Transactional
    public Order createNewOrder(OrderRequest request) {
        Order order = new Order();

        var employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Nhan vien khong ton tai"));
        order.setEmployee(employee);

        if (request.getCustomerId() != null) {
            customerRepository.findById(request.getCustomerId())
                    .ifPresent(order::setCustomer);
        }

        order.setOrderCode(generateUniqueOrderCode());
        order.setTotalAmount(BigDecimal.ZERO);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setFinalAmount(BigDecimal.ZERO);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(PaymentMethod.CASH);
        order.setCreatedAt(Instant.now());

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order updateCart(Long orderId, CartItemRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay don hang ID: " + orderId));

        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new RuntimeException("Don hang da chot, khong the chinh sua san pham");
        }

        ProductUnit unit = productUnitRepository.findById(request.getProductUnitId())
                .orElseThrow(() -> new RuntimeException("Ma don vi san pham khong hop le"));

        if (!unit.getIsActive()) {
            throw new RuntimeException("San pham nay hien dang ngung kinh doanh");
        }

        OrderDetail existingDetail = order.getOrderDetails().stream()
                .filter(d -> d.getProductUnit().getId().equals(request.getProductUnitId()))
                .findFirst()
                .orElse(null);

        if (existingDetail != null) {
            if (request.getQuantity() <= 0) {
                return removeItem(orderId, request.getProductUnitId());
            }
            existingDetail.setQuantity(request.getQuantity());
            existingDetail.setSubtotal(existingDetail.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        } else {
            if (request.getQuantity() > 0) {
                OrderDetail newDetail = new OrderDetail();
                newDetail.setOrder(order);
                newDetail.setProductUnit(unit);
                newDetail.setQuantity(request.getQuantity());
                newDetail.setUnitPrice(unit.getSellingPrice());
                newDetail.setSubtotal(unit.getSellingPrice().multiply(BigDecimal.valueOf(request.getQuantity())));

                orderDetailRepository.save(newDetail);
                order.getOrderDetails().add(newDetail);
            }
        }

        recalculateOrderFinancials(order);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order removeItem(Long orderId, Long productUnitId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay don hang ID: " + orderId));

        order.getOrderDetails().removeIf(detail -> {
            if (detail.getProductUnit().getId().equals(productUnitId)) {
                orderDetailRepository.delete(detail);
                return true;
            }
            return false;
        });

        recalculateOrderFinancials(order);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order checkout(Long orderId, CheckoutRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Don hang khong ton tai"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Don hang nay da duoc xu ly truoc do");
        }

        if (order.getOrderDetails().isEmpty()) {
            throw new RuntimeException("Don hang trong, khong the thanh toan");
        }

        PaymentMethod method = request.getPaymentMethod() != null ? request.getPaymentMethod() : PaymentMethod.CASH;
        finalizeOrder(order, method);
        upsertPayment(order, method, request.getAmountPaid(), "MANUAL-" + order.getOrderCode(), null, PaymentStatus.SUCCESS);

        return order;
    }

    @Override
    @Transactional
    public QrCheckoutResponse createQrForOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay don hang"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Don hang nay da duoc xu ly truoc do");
        }

        List<OrderDetail> details = orderDetailRepository.findByOrder_Id(order.getId());
        if (details.isEmpty()) {
            throw new RuntimeException("Don hang trong, khong the tao ma QR");
        }

        BigDecimal totalFromDetails = details.stream()
                .map(OrderDetail::getSubtotal)
                .filter(v -> v != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO;
        BigDecimal amount = totalFromDetails.subtract(discount);

        order.setTotalAmount(totalFromDetails);
        order.setFinalAmount(amount);
        orderRepository.save(order);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("So tien thanh toan khong hop le");
        }

        QrCheckoutResponse qrResponse;
        if (isPayOsReady()) {
            QrCheckoutResponse reusablePayOsLink = findReusablePayOsLink(order, amount);
            qrResponse = reusablePayOsLink != null ? reusablePayOsLink : createPayOsPayment(order, amount);
        } else {
            String addInfo = URLEncoder.encode("Thanh toan " + order.getOrderCode(), StandardCharsets.UTF_8);
            String qrUrl = String.format(
                    "https://img.vietqr.io/image/%s-%s-%s.png?amount=%s&addInfo=%s",
                    qrBankCode,
                    qrAccountNo,
                    qrTemplate,
                    amount.stripTrailingZeros().toPlainString(),
                    addInfo
            );

            qrResponse = QrCheckoutResponse.builder()
                    .orderId(order.getId())
                    .orderCode(order.getOrderCode())
                    .amount(amount)
                    .qrUrl(qrUrl)
                    .provider("VIETQR")
                    .build();
        }

        upsertPayment(order, PaymentMethod.QR_CODE, amount, null, toJsonSafely(qrResponse), PaymentStatus.PENDING);

        return qrResponse;
    }

    @Override
    @Transactional
    public Order confirmQrPayment(Long orderId, BigDecimal amountPaid, String transactionRef, String rawPayload) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay don hang"));

        if (order.getStatus() != OrderStatus.PENDING) {
            return order;
        }

        BigDecimal expected = order.getFinalAmount() != null ? order.getFinalAmount() : BigDecimal.ZERO;
        BigDecimal paid = amountPaid != null ? amountPaid : BigDecimal.ZERO;
        if (paid.compareTo(expected) < 0) {
            throw new RuntimeException("So tien thanh toan chua du cho don hang " + order.getOrderCode());
        }

        if (transactionRef != null && !transactionRef.isBlank() && paymentRepository.existsByTransactionRef(transactionRef)) {
            return order;
        }

        finalizeOrder(order, PaymentMethod.QR_CODE);
        upsertPayment(order, PaymentMethod.QR_CODE, paid, transactionRef, rawPayload, PaymentStatus.SUCCESS);

        return order;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay don hang voi ID: " + id));
    }

    @Override
    @Transactional
    public Order syncOnlinePaymentStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay don hang voi ID: " + orderId));

        if (order.getStatus() != OrderStatus.PENDING || !isPayOsReady()) {
            return order;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-client-id", payOsClientId.trim());
            headers.set("x-api-key", payOsApiKey.trim());

            String url = safeText(payOsApiUrl).replaceAll("/+$", "") + "/v2/payment-requests/" + orderId;
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    JsonNode.class
            );

            JsonNode body = response.getBody();
            if (body == null || !"00".equals(body.path("code").asText())) {
                return order;
            }

            JsonNode data = body.path("data");
            String status = safeText(data.path("status").asText()).toUpperCase();
            if (!"PAID".equals(status)) {
                return order;
            }

            BigDecimal paidAmount = data.path("amountPaid").isNumber()
                    ? data.path("amountPaid").decimalValue()
                    : order.getFinalAmount();
            String paymentRef = safeText(data.path("reference").asText());
            if (paymentRef.isEmpty()) {
                paymentRef = "PAYOS-SYNC-" + orderId;
            }

            return confirmQrPayment(order.getId(), paidAmount, paymentRef, body.toString());
        } catch (Exception ignored) {
            return order;
        }
    }

    @Override
    public List<Order> getPendingOrders() {
        return orderRepository.findTop20ByStatusOrderByCreatedAtDesc(OrderStatus.PENDING);
    }

    private void deductInventory(ProductUnit unit, int quantityToDeduct) {
        List<InventoryBatch> batches = inventoryBatchRepository
                .findByProductUnitAndQuantityAvailableGreaterThanOrderByCreatedAtAsc(unit, 0);

        int remaining = quantityToDeduct;

        for (InventoryBatch batch : batches) {
            if (remaining <= 0) break;

            int batchQty = batch.getQuantityAvailable();
            if (batchQty >= remaining) {
                batch.setQuantityAvailable(batchQty - remaining);
                remaining = 0;
            } else {
                remaining -= batchQty;
                batch.setQuantityAvailable(0);
            }
            inventoryBatchRepository.save(batch);
        }

        if (remaining > 0) {
            throw new RuntimeException("San pham " + unit.getUnitName() + " khong du ton kho");
        }
    }

    private void recalculateOrderFinancials(Order order) {
        BigDecimal total = order.getOrderDetails().stream()
                .map(OrderDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);

        BigDecimal discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO;
        order.setFinalAmount(total.subtract(discount));
    }

    private void finalizeOrder(Order order, PaymentMethod paymentMethod) {
        for (OrderDetail detail : order.getOrderDetails()) {
            deductInventory(detail.getProductUnit(), detail.getQuantity());
        }

        order.setPaymentMethod(paymentMethod);
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }

    private void upsertPayment(
            Order order,
            PaymentMethod paymentMethod,
            BigDecimal amount,
            String transactionRef,
            String payload,
            PaymentStatus paymentStatus
    ) {
        Payment payment = paymentRepository
                .findTopByOrder_IdAndPaymentMethodAndStatusOrderByCreatedAtDesc(
                        order.getId(),
                        paymentMethod,
                        PaymentStatus.PENDING
                )
                .orElseGet(Payment::new);

        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(amount != null ? amount : order.getFinalAmount());
        payment.setTransactionRef(transactionRef);
        payment.setPaymentPayload(payload);
        payment.setStatus(paymentStatus);
        if (payment.getCreatedAt() == null) {
            payment.setCreatedAt(Instant.now());
        }

        paymentRepository.save(payment);
    }

    private String generateUniqueOrderCode() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        String randomPart = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        String code = "ORD-" + datePart + "-" + randomPart;

        if (orderRepository.existsByOrderCode(code)) {
            return generateUniqueOrderCode();
        }
        return code;
    }

    private boolean isPayOsReady() {
        return payOsEnabled
                && !safeText(payOsClientId).isEmpty()
                && !safeText(payOsApiKey).isEmpty()
                && !safeText(payOsChecksumKey).isEmpty()
                && !safeText(payOsApiUrl).isEmpty();
    }

    private QrCheckoutResponse createPayOsPayment(Order order, BigDecimal amount) {
        long payOsOrderCode = order.getId();
        int amountInt = amount.setScale(0, java.math.RoundingMode.HALF_UP).intValueExact();
        String description = "TT " + order.getOrderCode();
        String returnUrl = appendQueryParam(
                appendQueryParam(payOsReturnUrl, "provider", "payos"),
                "orderId",
                String.valueOf(order.getId())
        );
        String cancelUrl = appendQueryParam(
                appendQueryParam(payOsCancelUrl, "provider", "payos"),
                "orderId",
                String.valueOf(order.getId())
        );
        String signatureData = String.format(
                "amount=%d&cancelUrl=%s&description=%s&orderCode=%d&returnUrl=%s",
                amountInt, cancelUrl, description, payOsOrderCode, returnUrl
        );
        String signature = hmacSha256(signatureData, payOsChecksumKey);

        JsonNode requestBody = objectMapper.createObjectNode()
                .put("orderCode", payOsOrderCode)
                .put("amount", amountInt)
                .put("description", description)
                .put("cancelUrl", cancelUrl)
                .put("returnUrl", returnUrl)
                .put("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-client-id", payOsClientId.trim());
        headers.set("x-api-key", payOsApiKey.trim());

        ResponseEntity<JsonNode> response;
        try {
            response = restTemplate.postForEntity(
                    safeText(payOsApiUrl).replaceAll("/+$", "") + "/v2/payment-requests",
                    new HttpEntity<>(requestBody, headers),
                    JsonNode.class
            );
        } catch (HttpClientErrorException.TooManyRequests e) {
            throw new RuntimeException("PayOS dang gioi han tan suat. Vui long thu lai sau it giay.");
        }

        JsonNode body = response.getBody();
        if (body == null || !"00".equals(body.path("code").asText())) {
            throw new RuntimeException("Tao link PayOS that bai");
        }

        JsonNode data = body.path("data");
        String checkoutUrl = safeText(data.path("checkoutUrl").asText());
        String qrCode = safeText(data.path("qrCode").asText());
        String qrUrl = qrCode.isEmpty()
                ? ""
                : "https://api.qrserver.com/v1/create-qr-code/?size=320x320&data="
                + URLEncoder.encode(qrCode, StandardCharsets.UTF_8);

        return QrCheckoutResponse.builder()
                .orderId(order.getId())
                .orderCode(order.getOrderCode())
                .amount(amount)
                .qrUrl(qrUrl)
                .qrCode(qrCode)
                .checkoutUrl(checkoutUrl)
                .provider("PAYOS")
                .build();
    }

    private String hmacSha256(String data, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            throw new RuntimeException("Khong the tao chu ky PayOS", e);
        }
    }

    private String safeText(String value) {
        return value == null ? "" : value.trim();
    }

    private String appendQueryParam(String baseUrl, String key, String value) {
        String normalized = safeText(baseUrl);
        if (normalized.isEmpty()) return normalized;

        String separator = normalized.contains("?") ? "&" : "?";
        return normalized
                + separator
                + URLEncoder.encode(key, StandardCharsets.UTF_8)
                + "="
                + URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private QrCheckoutResponse findReusablePayOsLink(Order order, BigDecimal amount) {
        Payment pending = paymentRepository
                .findTopByOrder_IdAndPaymentMethodAndStatusOrderByCreatedAtDesc(
                        order.getId(),
                        PaymentMethod.QR_CODE,
                        PaymentStatus.PENDING
                )
                .orElse(null);

        if (pending == null || pending.getPaymentPayload() == null || pending.getPaymentPayload().isBlank()) {
            return null;
        }

        try {
            JsonNode node = objectMapper.readTree(pending.getPaymentPayload());
            String provider = safeText(node.path("provider").asText());
            String checkoutUrl = safeText(node.path("checkoutUrl").asText());
            String qrCode = safeText(node.path("qrCode").asText());
            String qrUrl = safeText(node.path("qrUrl").asText());
            BigDecimal payloadAmount = node.path("amount").isNumber() ? node.path("amount").decimalValue() : amount;

            if (!"PAYOS".equalsIgnoreCase(provider) || checkoutUrl.isEmpty()) {
                return null;
            }
            if (payloadAmount.compareTo(amount) != 0) {
                return null;
            }

            return QrCheckoutResponse.builder()
                    .orderId(order.getId())
                    .orderCode(order.getOrderCode())
                    .amount(amount)
                    .qrUrl(qrUrl)
                    .qrCode(qrCode)
                    .checkoutUrl(checkoutUrl)
                    .provider("PAYOS")
                    .build();
        } catch (JsonProcessingException ignored) {
            return null;
        }
    }

    private String toJsonSafely(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ignored) {
            return "";
        }
    }

    @Scheduled(fixedDelayString = "${app.payment.payos.poll-ms:7000}")
    public void pollPayOsPendingOrders() {
        if (!isPayOsReady()) {
            return;
        }

        List<Order> pendingOrders = orderRepository.findTop20ByStatusOrderByCreatedAtDesc(OrderStatus.PENDING);
        for (Order pending : pendingOrders) {
            try {
                syncOnlinePaymentStatus(pending.getId());
            } catch (Exception ignored) {
                // Keep polling next orders even if one request fails.
            }
        }
    }
}
