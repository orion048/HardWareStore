package com.project.service;

import com.hardwarestore.common.dto.order.*;
import com.hardwarestore.common.order.OrderStatus;
import com.project.model.OrderEntity;
import com.project.model.OrderItemEntity;
import com.project.repository.OrderRepository;
import com.project.client.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderResponse createOrder(CreateOrderRequest request) {

        OrderEntity order = new OrderEntity();
        order.setUserId(request.getUserId());
        order.setStatus(OrderStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {

            BigDecimal price = productClient.getProductPrice(itemReq.getProductId());
            //BigDecimal price = itemReq.getPrice(); // временно

            OrderItemEntity item = new OrderItemEntity();
            item.setProductId(itemReq.getProductId());
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(price);
            item.setOrder(order);

            order.getItems().add(item);

            total = total.add(price.multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }

        order.setTotalAmount(total);

        OrderEntity saved = orderRepository.save(order);

        return mapToResponse(saved);
    }

    public OrderResponse getOrder(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(OrderEntity entity) {
        OrderResponse dto = new OrderResponse();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setTotalAmount(entity.getTotalAmount());

        dto.setItems(
                entity.getItems().stream().map(item -> {
                    OrderItemResponse r = new OrderItemResponse();
                    r.setProductId(item.getProductId());
                    r.setQuantity(item.getQuantity());
                    r.setPrice(item.getPrice());
                    return r;
                }).toList()
        );

        return dto;
    }
}

