package com.example.localpos.modules.pos.service;

import com.example.localpos.modules.pos.dto.request.CartItemRequest;
import com.example.localpos.modules.pos.dto.request.CheckoutRequest;
import com.example.localpos.modules.pos.dto.request.OrderRequest;
import com.example.localpos.modules.pos.entity.Order;

public interface OrderService {
    Order createNewOrder(OrderRequest request);

    // Thêm hoặc cập nhật số lượng ProductUnit trong Order
    Order updateCart(Long orderId, CartItemRequest request);

    // Xóa một dòng ProductUnit khỏi đơn hàng
    Order removeItem(Long orderId, Long productUnitId);

    Order checkout(Long orderId, CheckoutRequest request);

    Order getOrderById(Long orderId);
}
