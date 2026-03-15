package com.project.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaTestListener {

    @KafkaListener(topics = "saga.events.order.delivered", groupId = "order-test")
    public void listenDelivered(String message) {
        log.warn("🔥 RECEIVED FROM KAFKA (DELIVERED): {}", message);
    }

    @KafkaListener(topics = "saga.events.order.paid", groupId = "order-test")
    public void listenPaid(String message) {
        log.warn("🔥 RECEIVED FROM KAFKA (PAID): {}", message);
    }

    @KafkaListener(topics = "saga.events.order.cancelled", groupId = "order-test")
    public void listenCancelled(String message) {
        log.warn("🔥 RECEIVED FROM KAFKA (CANCELLED): {}", message);
    }
}

