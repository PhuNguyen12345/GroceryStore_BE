package com.example.localpos.modules.pos.controller;

import com.example.localpos.modules.pos.dto.request.CartItemRequest;
import com.example.localpos.modules.pos.dto.request.CheckoutRequest;
import com.example.localpos.modules.pos.dto.request.OrderRequest;
import com.example.localpos.modules.pos.dto.response.OrderResponse;
import com.example.localpos.modules.pos.dto.response.QrCheckoutResponse;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.mapper.OrderMapper;
import com.example.localpos.modules.pos.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
