package com.project.Event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventListener {

    @KafkaListener(topics = "order-created", groupId = "saga-coordinator")
    public void handleOrderCreated(String message) {
        System.out.println("SagaCoordinator получил событие: " + message);

        // Здесь можно вызвать Payment и Delivery сервисы
        // Например:
        // 1. Проверить оплату
        // 2. Запустить доставку
    }
}