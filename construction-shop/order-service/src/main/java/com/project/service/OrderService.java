package com.project.service;

import com.hardwarestore.common.dto.order.CreateOrderRequest;
import com.hardwarestore.common.dto.order.OrderItemRequest;
import com.hardwarestore.common.order.OrderStatus;

import com.project.dto.OrderResponse;
import com.project.dto.OrderItemResponse;

import com.project.event.OrderCancelledEvent;
import com.project.event.OrderDeliveredEvent;
import com.project.event.OrderPaidEvent;
import com.project.model.OrderEntity;
import com.project.model.OrderItemEntity;
import com.project.producer.OrderEventProducer;
import com.project.repository.OrderRepository;
import com.project.client.ProductClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderEventProducer eventProducer;

    // -----------------------------
    // CREATE ORDER
    // -----------------------------
    public OrderResponse createOrder(CreateOrderRequest request) {

        OrderEntity order = new OrderEntity();
        order.setUserId(request.getUserId());
        order.setStatus(OrderStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {

            BigDecimal price = productClient.getProductPrice(itemReq.getProductId());

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

    // -----------------------------
    // GET ORDER
    // -----------------------------
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

    // -----------------------------
    // APPROVE ORDER (PAYMENT SUCCESS)
    // -----------------------------
    public void approveOrder(Long orderId, Long paymentId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        eventProducer.sendOrderPaid(new OrderPaidEvent(orderId, paymentId));

        log.info("Order {} approved (PAID)", orderId);
    }

    // -----------------------------
    // CANCEL ORDER
    // -----------------------------
    public void cancelOrder(Long orderId, String reason) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelReason(reason);
        orderRepository.save(order);

        eventProducer.sendOrderCancelled(new OrderCancelledEvent(orderId, reason));

        log.warn("Order {} cancelled. Reason: {}", orderId, reason);
    }

    // -----------------------------
    // COMPLETE ORDER (DELIVERY SUCCESS)
    // -----------------------------
    public void completeOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);

        eventProducer.sendOrderDelivered(new OrderDeliveredEvent(orderId));

        log.info("Order {} completed (DELIVERED)", orderId);
    }

    // -----------------------------
    // MAPPER
    // -----------------------------
    private OrderResponse mapToResponse(OrderEntity entity) {

        List<OrderItemResponse> items = new ArrayList<>();

        for (OrderItemEntity item : entity.getItems()) {
            items.add(new OrderItemResponse(
                    item.getProductId(),
                    item.getQuantity(),
                    item.getPrice()
            ));
        }

        return new OrderResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getTotalAmount(),
                entity.getStatus().name(),
                entity.getCreatedAt(),
                items
        );
    }
}
