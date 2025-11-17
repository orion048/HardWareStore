package com.project.Service;

import com.project.Client.ProductClient;
import com.project.Client.UserClient;
import com.project.Event.OrderEventProducer;
import com.project.Model.Order;
import com.project.Repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final OrderEventProducer eventProducer;

    public OrderService(OrderRepository orderRepository,
                        UserClient userClient,
                        ProductClient productClient,
                        OrderEventProducer eventProducer) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.productClient = productClient;
        this.eventProducer = eventProducer;
    }

    public Order createOrder(Order order) {
        // 1. Проверка пользователя
        var user = userClient.getUserById(order.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        // 2. Проверка продукта
        var product = productClient.getProductById(order.getProductId());
        if (product == null || product.getQuantity() < 1) {
            throw new IllegalArgumentException("Product not available");
        }

        // 3. Сохраняем заказ
        Order saved = orderRepository.save(order);

        // 4. Отправляем событие в Kafka
        String eventPayload = "{ \"orderId\": " + saved.getId() +
                ", \"userId\": " + saved.getUserId() +
                ", \"productId\": " + saved.getProductId() + " }";
        eventProducer.sendOrderCreatedEvent(eventPayload);

        return saved;
    }
}
