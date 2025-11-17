package com.project.Event;

import com.project.Client.DeliveryClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventListener {

    private final DeliveryClient deliveryClient;

    public PaymentEventListener(DeliveryClient deliveryClient) {
        this.deliveryClient = deliveryClient;
    }

    @KafkaListener(topics = "order-paid", groupId = "saga-coordinator")
    public void handleOrderPaid(String message) {
        System.out.println("SagaCoordinator получил событие order-paid: " + message);

        try {
            // Допустим, сообщение содержит orderId
            Long orderId = Long.parseLong(message.replaceAll("[^0-9]", ""));
            // Вызов Delivery‑service через Feign
            String response = deliveryClient.startDelivery(orderId);
            System.out.println("SagaCoordinator инициировал доставку: " + response);
        } catch (Exception e) {
            System.err.println("Ошибка обработки события order-paid: " + e.getMessage());
        }
    }
}