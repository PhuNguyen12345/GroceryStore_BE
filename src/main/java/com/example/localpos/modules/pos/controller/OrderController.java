package com.example.localpos.modules.pos.controller;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.enums.OrderStatus;
import com.example.localpos.modules.pos.dto.request.CartItemRequest;
import com.example.localpos.modules.pos.dto.request.CheckoutRequest;
import com.example.localpos.modules.pos.dto.request.OrderRequest;
import com.example.localpos.modules.pos.dto.response.OrderAdminResponse;
import com.example.localpos.modules.pos.dto.response.OrderResponse;
import com.example.localpos.modules.pos.dto.response.QrCheckoutResponse;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.mapper.OrderMapper;
import com.example.localpos.modules.pos.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pos/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/init")
    public ResponseEntity<Order> initOrder(@RequestBody OrderRequest request) {
        Order newOrder = orderService.createNewOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @PutMapping("/{id}/items")
    public ResponseEntity<OrderResponse> updateCart(
            @PathVariable Long id,
            @RequestBody CartItemRequest request) {
        Order updatedOrder = orderService.updateCart(id, request);
        return ResponseEntity.ok(orderMapper.toDTO(updatedOrder));
    }

    @DeleteMapping("/{id}/items/{productUnitId}")
    public ResponseEntity<Order> removeItem(
            @PathVariable Long id,
            @PathVariable Long productUnitId) {
        Order updatedOrder = orderService.removeItem(id, productUnitId);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<Order> checkout(
            @PathVariable Long id,
            @RequestBody CheckoutRequest request) {
        Order finalizedOrder = orderService.checkout(id, request);
        return ResponseEntity.ok(finalizedOrder);
    }

    @PostMapping("/{id}/create-qr")
    public ResponseEntity<QrCheckoutResponse> createQr(@PathVariable Long id) {
        QrCheckoutResponse response = orderService.createQrForOrder(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/{id}/sync-payment")
    public ResponseEntity<Order> syncPayment(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.syncOnlinePaymentStatus(id));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<OrderResponse>> getPendingOrders() {
        List<OrderResponse> pending = orderService.getPendingOrders()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
        return ResponseEntity.ok(pending);
    }

    @GetMapping("/admin")
    public ResponseEntity<PageResponse<OrderAdminResponse>> getOrdersForAdmin(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDateTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDateTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String orderCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Instant fromTime = resolveFromInstant(fromDateTime, fromDate);
        Instant toTime = resolveToInstant(toDateTime, toDate);

        PageResponse<OrderAdminResponse> response = orderService.searchOrdersForAdmin(
                fromTime,
                toTime,
                status,
                orderCode,
                pageable
        );
        return ResponseEntity.ok(response);
    }

    private Instant resolveFromInstant(LocalDateTime fromDateTime, LocalDate fromDate) {
        if (fromDateTime != null) {
            return fromDateTime.atZone(ZoneId.systemDefault()).toInstant();
        }
        if (fromDate != null) {
            return fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
        return null;
    }

    private Instant resolveToInstant(LocalDateTime toDateTime, LocalDate toDate) {
        if (toDateTime != null) {
            return toDateTime.atZone(ZoneId.systemDefault()).toInstant();
        }
        if (toDate != null) {
            return toDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
        }
        return null;
    }
}
