package com.example.localpos.modules.pos.service;

import com.example.localpos.enums.OrderStatus;
import com.example.localpos.enums.PaymentMethod;
import com.example.localpos.enums.PaymentStatus;
import com.example.localpos.modules.crm.entity.Customer;
import com.example.localpos.modules.crm.repository.CustomerRepository;
import com.example.localpos.modules.crm.repository.VoucherRepository;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.pos.dto.request.CartItemRequest;
import com.example.localpos.modules.pos.dto.request.CheckoutRequest;
import com.example.localpos.modules.pos.dto.request.OrderRequest;
import com.example.localpos.modules.pos.dto.response.CheckoutResponse;
import com.example.localpos.modules.pos.dto.response.QrResponse;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.entity.OrderDetail;
import com.example.localpos.modules.inventory.repository.InventoryBatchRepository;
import com.example.localpos.modules.pos.entity.Payment;
import com.example.localpos.modules.pos.repository.OrderDetailRepository;
import com.example.localpos.modules.pos.repository.OrderRepository;
import com.example.localpos.modules.pos.repository.PaymentRepository;
import com.example.localpos.modules.product.repository.ProductUnitRepository;
import com.example.localpos.modules.product.entity.ProductUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductUnitRepository productUnitRepository;
    private final InventoryBatchRepository inventoryBatchRepository;
    private final PaymentRepository paymentRepository;
    private final VietQrService vietQrService;
    private final VoucherRepository voucherRepository;
    @Override
    @Transactional
    public Order createNewOrder(OrderRequest request) {
        Order order = new Order();

        // 1. Kiểm tra & Gán Nhân viên (Bắt buộc trong POS)
        var employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại!"));
        order.setEmployee(employee);

        // 2. Gán Khách hàng (Nếu có - khách vãng lai thì để null)
        if (request.getCustomerId() != null) {
            customerRepository.findById(request.getCustomerId())
                    .ifPresent(order::setCustomer);
        }

        // 3. Logic tạo mã đơn hàng (Ví dụ: ORD-20240311-XXXX)
        order.setOrderCode(generateUniqueOrderCode());

        // 4. Thiết lập các giá trị tài chính mặc định
        order.setTotalAmount(BigDecimal.ZERO);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setFinalAmount(BigDecimal.ZERO);

        // 5. Trạng thái và Thời gian
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(PaymentMethod.CASH);
        order.setCreatedAt(Instant.now());

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order updateCart(Long orderId, CartItemRequest request) {
        // 1. Kiểm tra đơn hàng hiện tại
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + orderId));

        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new RuntimeException("Đơn hàng đã chốt, không thể chỉnh sửa sản phẩm.");
        }

        // 2. Kiểm tra Đơn vị sản phẩm (ProductUnit)
        ProductUnit unit = productUnitRepository.findById(request.getProductUnitId())
                .orElseThrow(() -> new RuntimeException("Mã đơn vị sản phẩm không hợp lệ."));

        if (!unit.getIsActive()) {
            throw new RuntimeException("Sản phẩm này hiện đang ngừng kinh doanh.");
        }

        // 3. Xử lý logic thêm/sửa trong Set orderDetails
        // Tìm xem ProductUnit này đã tồn tại trong đơn hàng chưa
        OrderDetail existingDetail = order.getOrderDetails().stream()
                .filter(d -> d.getProductUnit().getId().equals(request.getProductUnitId()))
                .findFirst()
                .orElse(null);

        if (existingDetail != null) {
            // Nếu đã có: Cập nhật số lượng mới
            if (request.getQuantity() <= 0) {
                return removeItem(orderId, request.getProductUnitId());
            }
            existingDetail.setQuantity(request.getQuantity());
            // Tính lại subtotal cho dòng này
            existingDetail.setSubtotal(existingDetail.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        } else {
            // Nếu chưa có: Tạo mới OrderDetail
            if (request.getQuantity() > 0) {
                OrderDetail newDetail = new OrderDetail();
                newDetail.setOrder(order);
                newDetail.setProductUnit(unit);
                newDetail.setQuantity(request.getQuantity());

                // LẤY GIÁ BÁN TẠI THỜI ĐIỂM BÁN TỪ PRODUCT_UNIT
                newDetail.setUnitPrice(unit.getSellingPrice());
                newDetail.setSubtotal(unit.getSellingPrice().multiply(BigDecimal.valueOf(request.getQuantity())));

                // Lưu vào DB và thêm vào Set của Order
                orderDetailRepository.save(newDetail);
                order.getOrderDetails().add(newDetail);
            }
        }

        // 4. Tính toán lại tổng tiền của toàn bộ Order
        recalculateOrderFinancials(order);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public CheckoutResponse checkout(Long orderId, CheckoutRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("Order already paid");
        }

        BigDecimal total = calculateTotal(order);

        // ===== 1. VOUCHER =====
        BigDecimal voucherDiscount = applyVoucher(request.getVoucherId(), total);

        BigDecimal afterVoucher = total.subtract(voucherDiscount).max(BigDecimal.ZERO);

        // ===== 2. LOYALTY =====
        int usedPoints = request.getUsedPoints() != null ? request.getUsedPoints() : 0;

        int validPoints = 0;
        BigDecimal pointDiscount = BigDecimal.ZERO;

        if (order.getCustomer() != null) {

            int maxPointsByMoney = afterVoucher.divide(BigDecimal.valueOf(1000)).intValue();

            validPoints = Math.min(
                    usedPoints,
                    Math.min(order.getCustomer().getLoyaltyPoints(), maxPointsByMoney)
            );

            pointDiscount = BigDecimal.valueOf(validPoints).multiply(BigDecimal.valueOf(1000));

            // TRỪ ĐIỂM
            order.getCustomer().setLoyaltyPoints(
                    order.getCustomer().getLoyaltyPoints() - validPoints
            );
        }

        // ===== 3. FINAL =====
        BigDecimal finalTotal = afterVoucher.subtract(pointDiscount);

        // ===== 4. CỘNG ĐIỂM =====
        int earnedPoints = finalTotal.divide(BigDecimal.valueOf(100000)).intValue();

        if (order.getCustomer() != null) {
            order.getCustomer().setLoyaltyPoints(
                    order.getCustomer().getLoyaltyPoints() + earnedPoints
            );
        }

        // ===== 5. TRỪ KHO =====
        for (OrderDetail detail : order.getOrderDetails()) {
            deductInventory(detail.getProductUnit(), detail.getQuantity());
        }

        // ===== 6. PAYMENT =====
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(finalTotal);
        payment.setPaymentMethod(PaymentMethod.QR_CODE);
        payment.setStatus(PaymentStatus.PENDING);

        paymentRepository.save(payment);

        // ===== 7. UPDATE ORDER =====
        order.setTotalAmount(total);
        order.setDiscountAmount(voucherDiscount.add(pointDiscount));
        order.setFinalAmount(finalTotal);
        order.setStatus(OrderStatus.PENDING); // chờ thanh toán QR

        orderRepository.save(order);

        // ===== 8. QR =====
        QrResponse qr = vietQrService.generateQr(finalTotal);

        return CheckoutResponse.builder()
                .total(total)
                .finalTotal(finalTotal)
                .usedPoints(validPoints)
                .earnedPoints(earnedPoints)
                .qr(qr)
                .build();
    }
    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<?> confirmPayment(@PathVariable Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow();

        order.setStatus(OrderStatus.COMPLETED);

        Payment payment = paymentRepository.findByOrder(order);
        payment.setStatus(PaymentStatus.SUCCESS);

        paymentRepository.save(payment);
        orderRepository.save(order);

        return ResponseEntity.ok("Payment success");
    }

    @Override
    @Transactional
    public Order removeItem(Long orderId, Long productUnitId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        // Loại bỏ khỏi Set và xóa trong DB
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
    private BigDecimal calculateTotal(Order order) {
        return order.getOrderDetails().stream()
                .map(OrderDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private BigDecimal applyVoucher(Long voucherId, BigDecimal total) {

        if (voucherId == null) return BigDecimal.ZERO;

        var voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));

        if (total.compareTo(voucher.getMinOrderValue()) < 0) {
            return BigDecimal.ZERO;
        }

        return voucher.getDiscountValue();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
    }

    private void deductInventory(ProductUnit unit, int quantityToDeduct) {
        // Lấy các lô hàng còn tồn kho của Unit này, sắp xếp theo ngày nhập/hạn sử dụng
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
            // Tùy chính sách cửa hàng: có cho phép bán âm kho không?
            // Ở đây tôi throw lỗi để đảm bảo tính chính xác
            throw new RuntimeException("Sản phẩm " + unit.getUnitName() + " không đủ tồn kho!");
        }
    }

//    private CheckoutResponse processCash(Order order) {
//
//        Payment payment = new Payment();
//
//        payment.setOrder(order);
//        payment.setAmount(order.getTotalAmount());
//        payment.setPaymentMethod(PaymentMethod.CASH);
//        payment.setStatus(PaymentStatus.SUCCESS);
//
//        paymentRepository.save(payment);
//
//        order.setStatus(OrderStatus.COMPLETED);
//
//        orderRepository.save(order);
//
//        return CheckoutResponse.builder()
//                .status("SUCCESS")
//                .message("Cash payment completed")
//                .build();
//    }
//
//    private CheckoutResponse processBankQr(Order order) {
//
//        BigDecimal amount;
//
//        if(order.getVoucher() != null){
//
//            amount = order.getTotalAmount()
//                    .subtract(order.getVoucher().getDiscountValue());
//
//            order.setDiscountAmount(order.getVoucher().getDiscountValue());
//            order.setFinalAmount(amount);
//
//        } else {
//
//            amount = order.getTotalAmount();
//            order.setFinalAmount(amount);
//
//        }
//
//        Payment payment = new Payment();
//        payment.setOrder(order);
//        payment.setAmount(amount);
//        payment.setPaymentMethod(PaymentMethod.QR_CODE);
//        payment.setStatus(PaymentStatus.PENDING);
//
//        paymentRepository.save(payment);
//
//        orderRepository.save(order);
//
//        String qrUrl = vietQrService.generateQr(amount).getQrUrl();
//
//        return CheckoutResponse.builder()
//                .status("PENDING")
//                .qrUrl(qrUrl)
//                .message("Scan QR to pay")
//                .build();
//    }

    private void recalculateOrderFinancials(Order order) {
        // Cộng tổng các subtotal từ các dòng chi tiết
        BigDecimal total = order.getOrderDetails().stream()
                .map(OrderDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);

        // Tính Final Amount
        BigDecimal discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO;
        order.setFinalAmount(total.subtract(discount));
    }

    /**
     * Tạo mã đơn hàng duy nhất dựa trên ngày và chuỗi random ngắn
     */
    private String generateUniqueOrderCode() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        String randomPart = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        String code = "ORD-" + datePart + "-" + randomPart;

        // Đệ quy kiểm tra nếu trùng
        if (orderRepository.existsByOrderCode(code)) {
            return generateUniqueOrderCode();
        }
        return code;
    }
}
