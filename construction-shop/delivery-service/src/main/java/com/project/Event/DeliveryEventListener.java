package com.project.Event;

import com.project.Service.DeliveryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DeliveryEventListener {

    private final DeliveryService deliveryService;

    public DeliveryEventListener(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @KafkaListener(topics = "order-paid", groupId = "delivery-service")
    public void handleOrderPaid(String message) {
        System.out.println("DeliveryService получил событие: " + message);

        try {
            // Допустим, сообщение содержит orderId
            Long orderId = Long.parseLong(message.replaceAll("[^0-9]", ""));
            deliveryService.startDelivery(orderId);
            System.out.println("Доставка запущена для заказа: " + orderId);
        } catch (Exception e) {
            System.err.println("Ошибка обработки события: " + e.getMessage());
        }
    }
}