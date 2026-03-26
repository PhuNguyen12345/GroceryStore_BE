package com.example.localpos.modules.pos.service;

import com.example.localpos.enums.DiscountType;
import com.example.localpos.enums.OrderStatus;
import com.example.localpos.enums.PaymentMethod;
import com.example.localpos.enums.PaymentStatus;
import com.example.localpos.modules.crm.entity.Customer;
import com.example.localpos.modules.crm.entity.Voucher;
import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.crm.repository.CustomerRepository;
import com.example.localpos.modules.crm.repository.VoucherRepository;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.inventory.repository.InventoryBatchRepository;
import com.example.localpos.modules.pos.dto.request.CartItemRequest;
import com.example.localpos.modules.pos.dto.request.CheckoutRequest;
import com.example.localpos.modules.pos.dto.request.OrderRequest;
import com.example.localpos.modules.pos.dto.response.OrderAdminResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.concurrent.ThreadLocalRandom;
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
    private final VoucherRepository voucherRepository;
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
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay don hang ID: " + orderId));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Chi co the huy don hang dang cho");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order updateCustomer(Long orderId, Long customerId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay don hang ID: " + orderId));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Chi co the cap nhat don hang dang cho");
        }

        if (customerId == null) {
            order.setCustomer(null);
        } else {
            Customer c = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay khach hang ID: " + customerId));
            order.setCustomer(c);
        }

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

        // 1. Recalculate base total
        recalculateOrderFinancials(order);

        BigDecimal total = order.getTotalAmount();

        // 2. Apply voucher
        BigDecimal voucherDiscount = applyVoucher(order, request.getVoucherId());

        // 3. Apply loyalty points
        if (request.getCustomerId() != null) {
            Customer customer = customerRepository
                    .findById(request.getCustomerId())
                    .orElseThrow();

            order.setCustomer(customer);
        }
        BigDecimal pointsDiscount = applyPoints(order, request.getUsedPoints());

        // 4. Final amount
        BigDecimal finalAmount = total
                .subtract(voucherDiscount)
                .subtract(pointsDiscount);

        if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
            finalAmount = BigDecimal.ZERO;
        }

        order.setDiscountAmount(voucherDiscount.add(pointsDiscount));
        order.setFinalAmount(finalAmount);

        // 5. Validate amountPaid
        BigDecimal amountPaid = request.getAmountPaid() != null
                ? request.getAmountPaid()
                : BigDecimal.ZERO;

        if (amountPaid.compareTo(finalAmount) < 0) {
            throw new RuntimeException("Khach chua thanh toan du tien");
        }

        PaymentMethod method = request.getPaymentMethod() != null
                ? request.getPaymentMethod()
                : PaymentMethod.CASH;

        // 6. Finalize order
        finalizeOrder(order, method);
        if (request.getVoucherId() != null && !request.getVoucherId().trim().isEmpty()) {
            Voucher voucher = voucherRepository.findByCode(request.getVoucherId())
                    .orElseThrow(() -> new RuntimeException("Voucher khong ton tai"));
            increaseVoucherUsage(voucher);
        }

        // 7. Save payment
        upsertPayment(order, method, amountPaid,
                "MANUAL-" + order.getOrderCode(),
                null,
                PaymentStatus.SUCCESS);

        // 8. Reward points
        rewardPoints(order);

        return order;
    }

    @Override
    @Transactional
    public QrCheckoutResponse createQrForOrder(Long orderId, CheckoutRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay don hang"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Don hang nay da duoc xu ly truoc do");
        }

        List<OrderDetail> details = orderDetailRepository.findByOrder_Id(order.getId());
        if (details.isEmpty()) {
            throw new RuntimeException("Don hang trong, khong the tao ma QR");
        }

        recalculateOrderFinancials(order);
        BigDecimal total = order.getTotalAmount();
        
        BigDecimal voucherDiscount = BigDecimal.ZERO;
        BigDecimal pointsDiscount = BigDecimal.ZERO;

        if (request != null) {
            voucherDiscount = applyVoucher(order, request.getVoucherId());
            if (request.getCustomerId() != null) {
                Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
                if (customer != null) {
                    order.setCustomer(customer);
                }
            }
            if (request.getUsedPoints() != null) {
                pointsDiscount = applyPoints(order, request.getUsedPoints());
            }
        }

        BigDecimal finalAmount = total.subtract(voucherDiscount).subtract(pointsDiscount);
        if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
            finalAmount = BigDecimal.ZERO;
        }

        order.setDiscountAmount(voucherDiscount.add(pointsDiscount));
        order.setFinalAmount(finalAmount);
        orderRepository.save(order);

        if (finalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("So tien thanh toan khong hop le");
        }

        BigDecimal amount = finalAmount;

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

        String pendingTransactionRef = null;
        if ("PAYOS".equalsIgnoreCase(safeText(qrResponse.getProvider())) && qrResponse.getProviderOrderCode() != null) {
            pendingTransactionRef = "PAYOS-ORDERCODE-" + qrResponse.getProviderOrderCode();
        }
        upsertPayment(order, PaymentMethod.QR_CODE, amount, pendingTransactionRef, toJsonSafely(qrResponse), PaymentStatus.PENDING);

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
            // Find the active PayOS order code from the latest pending payment
            Payment pending = paymentRepository
                    .findTopByOrder_IdAndPaymentMethodAndStatusOrderByCreatedAtDesc(
                            order.getId(),
                            PaymentMethod.QR_CODE,
                            PaymentStatus.PENDING
                    ).orElse(null);

            long payOsOrderCode = orderId;
            if (pending != null && pending.getPaymentPayload() != null) {
                try {
                    JsonNode node = objectMapper.readTree(pending.getPaymentPayload());
                    if (node.has("payOsOrderCode")) {
                        payOsOrderCode = node.path("payOsOrderCode").asLong(orderId);
                    }
                } catch (Exception ignored) {}
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-client-id", payOsClientId.trim());
            headers.set("x-api-key", payOsApiKey.trim());

            String url = safeText(payOsApiUrl).replaceAll("/+$", "") + "/v2/payment-requests/" + payOsOrderCode;
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
                if (("CANCELLED".equals(status) || "EXPIRED".equals(status)) && pending != null) {
                    pending.setStatus(PaymentStatus.FAILED);
                    paymentRepository.save(pending);
                }
                return order;
            }

            BigDecimal paidAmount = data.path("amountPaid").isNumber()
                    ? data.path("amountPaid").decimalValue()
                    : order.getFinalAmount();
            String paymentRef = safeText(data.path("reference").asText());
            if (paymentRef.isEmpty()) {
                paymentRef = "PAYOS-SYNC-" + payOsOrderCode;
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

    @Override
    public PageResponse<OrderAdminResponse> searchOrdersForAdmin(
            Instant fromTime,
            Instant toTime,
            OrderStatus status,
            String orderCode,
            Pageable pageable
    ) {
        Page<OrderAdminResponse> page = orderRepository.searchOrdersForAdmin(
                fromTime,
                toTime,
                status,
                orderCode,
                pageable
        );

        return PageResponse.<OrderAdminResponse>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    private BigDecimal applyLoyaltyPoints(Order order, Integer usedPoints) {
        if (usedPoints == null || usedPoints <= 0) return BigDecimal.ZERO;

        if (order.getCustomer() == null) {
            throw new RuntimeException("Khong co khach hang de su dung points");
        }

        var customer = order.getCustomer();

        if (customer.getLoyaltyPoints() < usedPoints) {
            throw new RuntimeException("Khong du diem");
        }

        // 1 point = 1000đ (tuỳ bạn define)
        BigDecimal discount = BigDecimal.valueOf(usedPoints * 1000);

        // Trừ điểm
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() - usedPoints);
        customerRepository.save(customer);

        return discount;
    }

    private void rewardPoints(Order order) {
        if (order.getCustomer() == null) return;

        var customer = order.getCustomer();

        BigDecimal finalAmount = order.getFinalAmount();

        int earnedPoints = finalAmount
                .divide(BigDecimal.valueOf(10000), java.math.RoundingMode.DOWN)
                .intValue();

        if (earnedPoints > 0) {
            customer.setLoyaltyPoints(
                    customer.getLoyaltyPoints() + earnedPoints
            );
            customerRepository.save(customer);
        }
    }

    private BigDecimal applyVoucher(Order order, String voucherId) {

        if (voucherId == null || voucherId.trim().isEmpty()) return BigDecimal.ZERO;

        Voucher voucher = voucherRepository.findByCode(voucherId)
                .orElseThrow(() -> new RuntimeException("Voucher khong ton tai"));

        // ===== 1. ACTIVE =====
        if (!Boolean.TRUE.equals(voucher.getIsActive())) {
            throw new RuntimeException("Voucher khong hoat dong");
        }

        Instant now = Instant.now();

        // ===== 2. DATE VALID =====
        if (voucher.getStartDate() != null && now.isBefore(voucher.getStartDate())) {
            throw new RuntimeException("Voucher chua bat dau");
        }

        if (voucher.getEndDate() != null && now.isAfter(voucher.getEndDate())) {
            throw new RuntimeException("Voucher da het han");
        }

        // ===== 3. QUANTITY LIMIT =====
        int used = voucher.getQuantityUsed() != null ? voucher.getQuantityUsed() : 0;
        int limit = voucher.getQuantityLimit() != null ? voucher.getQuantityLimit() : Integer.MAX_VALUE;

        if (used >= limit) {
            throw new RuntimeException("Voucher da het luot su dung");
        }

        // ===== 4. MIN ORDER =====
        BigDecimal total = order.getTotalAmount() != null
                ? order.getTotalAmount()
                : BigDecimal.ZERO;

        if (voucher.getMinOrderValue() != null &&
                total.compareTo(voucher.getMinOrderValue()) < 0) {
            throw new RuntimeException("Don hang chua dat gia tri toi thieu");
        }

        // ===== 5. CALCULATE DISCOUNT =====
        BigDecimal discount;

        if (voucher.getDiscountType() == DiscountType.PERCENTAGE) {

            discount = total.multiply(voucher.getDiscountValue())
                    .divide(BigDecimal.valueOf(100), java.math.RoundingMode.HALF_UP);

        } else {
            discount = voucher.getDiscountValue();
        }

        // ===== 6. CLAMP =====
        if (discount.compareTo(total) > 0) {
            discount = total;
        }

        return discount;
    }
    private BigDecimal applyPoints(Order order, int usedPoints) {

        if (usedPoints <= 0) return BigDecimal.ZERO;

        Customer customer = order.getCustomer();

        if (customer == null) {
            throw new RuntimeException("Khong co khach hang de su dung points");
        }

        if (customer.getLoyaltyPoints() < usedPoints) {
            throw new RuntimeException("Khong du diem");
        }

        // ví dụ: 1 điểm = 1000đ
        return BigDecimal.valueOf(usedPoints * 1000);
    }
    private void increaseVoucherUsage(Voucher voucher) {
        voucher.setQuantityUsed(
                (voucher.getQuantityUsed() == null ? 0 : voucher.getQuantityUsed()) + 1
        );
        voucherRepository.save(voucher);
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
        long payOsOrderCode = generatePayOsOrderCode(order.getId());
        int amountInt = amount.setScale(0, java.math.RoundingMode.HALF_UP).intValueExact();
        String description = buildPayOsDescription(order.getId());
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
        } catch (HttpClientErrorException e) {
            String payOsCode = parsePayOsCode(e.getResponseBodyAsString());
            if ("231".equals(payOsCode)) {
                QrCheckoutResponse existingPayment = fetchExistingPayOsPayment(order, amount);
                if (existingPayment != null) {
                    return existingPayment;
                }
            }
            throw new RuntimeException(extractPayOsErrorMessage(e));
        }

        JsonNode body = response.getBody();
        if (body == null) {
            throw new RuntimeException("Tao link PayOS that bai: response rong");
        }

        String payOsCode = safeText(body.path("code").asText());
        if (!"00".equals(payOsCode)) {
            if ("231".equals(payOsCode)) {
                QrCheckoutResponse existingPayment = fetchExistingPayOsPayment(order, amount);
                if (existingPayment != null) {
                    return existingPayment;
                }
            }
            String payOsDesc = safeText(body.path("desc").asText());
            if (!payOsDesc.isEmpty()) {
                throw new RuntimeException("PayOS error " + payOsCode + ": " + payOsDesc);
            }
            throw new RuntimeException("PayOS error code: " + payOsCode);
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
                .providerOrderCode(payOsOrderCode)
                .amount(amount)
                .qrUrl(qrUrl)
                .qrCode(qrCode)
                .checkoutUrl(checkoutUrl)
                .provider("PAYOS")
                .payOsOrderCode(payOsOrderCode)
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

    private String parsePayOsCode(String body) {
        if (body == null || body.isBlank()) {
            return "";
        }
        try {
            JsonNode node = objectMapper.readTree(body);
            return safeText(node.path("code").asText());
        } catch (JsonProcessingException ignored) {
            return "";
        }
    }

    private QrCheckoutResponse fetchExistingPayOsPayment(Order order, BigDecimal expectedAmount) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-client-id", payOsClientId.trim());
            headers.set("x-api-key", payOsApiKey.trim());

            long payOsOrderCode = resolvePayOsOrderCodeForSync(order.getId());
            String url = safeText(payOsApiUrl).replaceAll("/+$", "") + "/v2/payment-requests/" + payOsOrderCode;
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    JsonNode.class
            );

            JsonNode body = response.getBody();
            if (body == null || !"00".equals(safeText(body.path("code").asText()))) {
                return null;
            }

            JsonNode data = body.path("data");
            String checkoutUrl = safeText(data.path("checkoutUrl").asText());
            String qrCode = safeText(data.path("qrCode").asText());
            if (checkoutUrl.isEmpty() && qrCode.isEmpty()) {
                return null;
            }

            BigDecimal amount = data.path("amount").isNumber()
                    ? data.path("amount").decimalValue()
                    : expectedAmount;
            if (amount.compareTo(expectedAmount) != 0) {
                return null;
            }

            String qrUrl = qrCode.isEmpty()
                    ? ""
                    : "https://api.qrserver.com/v1/create-qr-code/?size=320x320&data="
                    + URLEncoder.encode(qrCode, StandardCharsets.UTF_8);

            return QrCheckoutResponse.builder()
                    .orderId(order.getId())
                    .orderCode(order.getOrderCode())
                    .providerOrderCode(payOsOrderCode)
                    .amount(expectedAmount)
                    .qrUrl(qrUrl)
                    .qrCode(qrCode)
                    .checkoutUrl(checkoutUrl)
                    .provider("PAYOS")
                    .build();
        } catch (Exception ignored) {
            return null;
        }
    }

    private String buildPayOsDescription(Long orderId) {
        String value = "DH" + (orderId != null ? orderId : 0L);
        // PayOS note: some linked bank flows only accept up to 9 chars in description.
        if (value.length() <= 9) {
            return value;
        }
        return value.substring(value.length() - 9);
    }

    private String extractPayOsErrorMessage(HttpClientErrorException e) {
        String fallback = "Tao link PayOS that bai";
        String body = e.getResponseBodyAsString();
        if (body == null || body.isBlank()) {
            return fallback + " (" + e.getStatusCode().value() + ")";
        }

        try {
            JsonNode node = objectMapper.readTree(body);
            String code = safeText(node.path("code").asText());
            String desc = safeText(node.path("desc").asText());

            if (!desc.isEmpty() && !code.isEmpty()) {
                return "PayOS error " + code + ": " + desc;
            }
            if (!desc.isEmpty()) {
                return "PayOS: " + desc;
            }
            if (!code.isEmpty()) {
                return "PayOS error code: " + code;
            }
        } catch (JsonProcessingException ignored) {
            // Keep fallback below if body is not JSON.
        }

        return fallback + ": " + body;
    }

    private long generatePayOsOrderCode(Long orderId) {
        long now = System.currentTimeMillis();
        long randomPart = ThreadLocalRandom.current().nextInt(100, 1000);
        long orderPart = Math.abs(orderId != null ? orderId : 0L) % 100;
        return now * 1000 + randomPart + orderPart;
    }

    private long resolvePayOsOrderCodeForSync(Long orderId) {
        Payment pending = paymentRepository
                .findTopByOrder_IdAndPaymentMethodAndStatusOrderByCreatedAtDesc(
                        orderId,
                        PaymentMethod.QR_CODE,
                        PaymentStatus.PENDING
                )
                .orElse(null);

        if (pending == null) {
            return orderId;
        }

        long fromRef = parsePayOsOrderCodeFromRef(pending.getTransactionRef());
        if (fromRef > 0) {
            return fromRef;
        }

        String payload = pending.getPaymentPayload();
        if (payload != null && !payload.isBlank()) {
            try {
                JsonNode node = objectMapper.readTree(payload);
                if (node.path("providerOrderCode").isNumber()) {
                    return node.path("providerOrderCode").asLong();
                }
            } catch (JsonProcessingException ignored) {
                // Fallback below.
            }
        }

        return orderId;
    }

    private long parsePayOsOrderCodeFromRef(String transactionRef) {
        String ref = safeText(transactionRef);
        String prefix = "PAYOS-ORDERCODE-";
        if (!ref.startsWith(prefix)) {
            return -1;
        }
        try {
            return Long.parseLong(ref.substring(prefix.length()));
        } catch (NumberFormatException ignored) {
            return -1;
        }
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
            Long providerOrderCode = node.path("providerOrderCode").isNumber()
                    ? node.path("providerOrderCode").asLong()
                    : null;
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
                    .providerOrderCode(providerOrderCode)
                    .amount(amount)
                    .qrUrl(qrUrl)
                    .qrCode(qrCode)
                    .checkoutUrl(checkoutUrl)
                    .provider("PAYOS")
                    .payOsOrderCode(node.path("payOsOrderCode").asLong(order.getId()))
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
