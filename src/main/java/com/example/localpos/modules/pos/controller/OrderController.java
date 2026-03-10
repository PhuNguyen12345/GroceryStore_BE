package com.example.localpos.modules.pos.controller;

import com.example.localpos.modules.pos.dto.request.CartItemRequest;
import com.example.localpos.modules.pos.dto.request.CheckoutRequest;
import com.example.localpos.modules.pos.dto.request.OrderRequest;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pos/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderServiceImpl orderService;


    @PostMapping("/init")
    public ResponseEntity<Order> initOrder(@RequestBody OrderRequest request) {
        Order newOrder = orderService.createNewOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }


    @PutMapping("/{id}/items")
    public ResponseEntity<Order> updateCart(
            @PathVariable Long id,
            @RequestBody CartItemRequest request) {
        Order updatedOrder = orderService.updateCart(id, request);
        return ResponseEntity.ok(updatedOrder);
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

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        // Bạn có thể thêm method findById vào Service nếu chưa có
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
