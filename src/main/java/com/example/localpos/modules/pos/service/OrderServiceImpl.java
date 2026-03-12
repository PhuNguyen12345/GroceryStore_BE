package com.example.localpos.modules.pos.service;

import com.example.localpos.enums.OrderStatus;
import com.example.localpos.enums.PaymentMethod;
import com.example.localpos.modules.crm.repository.CustomerRepository;
import com.example.localpos.modules.hr.repository.EmployeeRepository;
import com.example.localpos.modules.inventory.entity.InventoryBatch;
import com.example.localpos.modules.pos.dto.request.CartItemRequest;
import com.example.localpos.modules.pos.dto.request.CheckoutRequest;
import com.example.localpos.modules.pos.dto.request.OrderRequest;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.entity.OrderDetail;
import com.example.localpos.modules.inventory.repository.InventoryBatchRepository;
import com.example.localpos.modules.pos.repository.OrderDetailRepository;
import com.example.localpos.modules.pos.repository.OrderRepository;
import com.example.localpos.modules.product.repository.ProductUnitRepository;
import com.example.localpos.modules.product.entity.ProductUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public Order checkout(Long orderId, CheckoutRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Đơn hàng này đã được xử lý trước đó.");
        }

        if (order.getOrderDetails().isEmpty()) {
            throw new RuntimeException("Đơn hàng trống, không thể thanh toán.");
        }

        // 1. Logic Trừ Kho (Inventory)
        for (OrderDetail detail : order.getOrderDetails()) {
            deductInventory(detail.getProductUnit(), detail.getQuantity());
        }

        // 2. Cập nhật thông tin đơn hàng
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(OrderStatus.COMPLETED); // Đổi trạng thái sang hoàn tất


        return orderRepository.save(order);
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
