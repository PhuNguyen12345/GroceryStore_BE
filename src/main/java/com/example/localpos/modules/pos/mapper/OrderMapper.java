package com.example.localpos.modules.pos.mapper;

import com.example.localpos.modules.pos.dto.response.OrderDetailResponse;
import com.example.localpos.modules.pos.dto.response.OrderResponse;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.entity.OrderDetail;
import com.example.localpos.modules.crm.dto.response.CustomerResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderResponse toDTO(Order order) {
        if (order == null) return null;

        OrderResponse dto = new OrderResponse();
        dto.setId(order.getId());
        dto.setOrderCode(order.getOrderCode());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());

        if (order.getEmployee() != null) {
            dto.setEmployeeName(order.getEmployee().getFullName());
        }

        if (order.getCustomer() != null) {
            CustomerResponse customerDTO = new CustomerResponse();
            customerDTO.setId(order.getCustomer().getId());
            customerDTO.setFullName(order.getCustomer().getFullName());
            customerDTO.setPhone(order.getCustomer().getPhone());
            customerDTO.setLoyaltyPoints(order.getCustomer().getLoyaltyPoints());
            customerDTO.setCustomerTier(order.getCustomer().getCustomerTier());
            dto.setCustomer(customerDTO);
        }

        if (order.getOrderDetails() != null) {
            dto.setOrderDetails(order.getOrderDetails().stream()
                    .map(this::toDetailDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
    private OrderDetailResponse toDetailDTO(OrderDetail detail) {
        OrderDetailResponse dto = new OrderDetailResponse();
        dto.setId(detail.getId());
        dto.setQuantity(detail.getQuantity());
        dto.setPrice(detail.getUnitPrice());
        if (detail.getUnitPrice() != null && detail.getQuantity() != null) {
            BigDecimal subtotal = detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity()));
            dto.setSubtotal(subtotal);
        }

        if (detail.getProductUnit() != null) {
            dto.setProductUnitId(detail.getProductUnit().getId());
            dto.setProductUnitName(detail.getProductUnit().getUnitName());
            // Lấy tên từ sản phẩm cha
            dto.setProductName(detail.getProductUnit().getProduct().getName());
        }

        return dto;
    }
}
