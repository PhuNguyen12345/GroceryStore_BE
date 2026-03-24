package com.example.localpos.modules.pos.service;

import com.example.localpos.modules.pos.dto.request.CartItemRequest;
import com.example.localpos.modules.pos.dto.request.CheckoutRequest;
import com.example.localpos.modules.pos.dto.request.OrderRequest;
import com.example.localpos.modules.pos.dto.response.QrCheckoutResponse;
import com.example.localpos.modules.pos.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order createNewOrder(OrderRequest request);

    Order updateCart(Long orderId, CartItemRequest request);

    Order removeItem(Long orderId, Long productUnitId);

    Order checkout(Long orderId, CheckoutRequest request);

    QrCheckoutResponse createQrForOrder(Long orderId);

    Order confirmQrPayment(Long orderId, BigDecimal amountPaid, String transactionRef, String rawPayload);

    Order getOrderById(Long orderId);

    Order syncOnlinePaymentStatus(Long orderId);

    List<Order> getPendingOrders();
}
