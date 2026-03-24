package com.example.localpos.modules.pos.controller;

import com.example.localpos.enums.OrderStatus;
import com.example.localpos.enums.PaymentStatus;
import com.example.localpos.modules.pos.dto.request.CartItemRequest;
import com.example.localpos.modules.pos.dto.request.CheckoutRequest;
import com.example.localpos.modules.pos.dto.request.OrderRequest;
import com.example.localpos.modules.pos.dto.response.CheckoutResponse;
import com.example.localpos.modules.pos.dto.response.OrderDetailResponse;
import com.example.localpos.modules.pos.dto.response.OrderResponse;
import com.example.localpos.modules.pos.dto.response.QrResponse;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.entity.Payment;
import com.example.localpos.modules.pos.mapper.OrderMapper;
import com.example.localpos.modules.pos.repository.OrderRepository;
import com.example.localpos.modules.pos.repository.PaymentRepository;
import com.example.localpos.modules.pos.service.OrderServiceImpl;
import com.example.localpos.modules.pos.service.VietQrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/pos/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderServiceImpl orderService;

    private final OrderMapper orderMapper;
    private final VietQrService vietQrService;

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


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        // Bạn có thể thêm method findById vào Service nếu chưa có
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/{orderId}/checkout")
    public ResponseEntity<CheckoutResponse> checkout(
            @PathVariable Long orderId,
            @RequestBody CheckoutRequest request
    ) {

        CheckoutResponse response = orderService.checkout(orderId, request);

        return ResponseEntity.ok(response);
    }
}
