package com.project.Event;

import com.project.Client.PaymentClient;
import com.project.DTO.PaymentDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderCreatedEventListener {

    private final PaymentClient paymentClient;

    public OrderCreatedEventListener(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @KafkaListener(topics = "order-created", groupId = "saga-coordinator")
    public void handleOrderCreated(String message) {
        System.out.println("SagaCoordinator получил событие order-created: " + message);

        try {
            // Допустим, сообщение содержит orderId
            Long orderId = Long.parseLong(message.replaceAll("[^0-9]", ""));
            // Вызов Payment‑service через Feign
            PaymentDto response = paymentClient.processPayment(orderId, 100.0);
            System.out.println("SagaCoordinator инициировал оплату: " + response.getStatus());
        } catch (Exception e) {
            System.err.println("Ошибка обработки события order-created: " + e.getMessage());
        }
    }
}