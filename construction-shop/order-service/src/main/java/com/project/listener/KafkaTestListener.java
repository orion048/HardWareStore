package com.project.listener;

import com.project.event.OrderCancelledEvent;
import com.project.event.OrderDeliveredEvent;
import com.project.event.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaTestListener {

    @KafkaListener(
            topics = "saga.events.order.delivered",
            groupId = "order-test",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenDelivered(OrderDeliveredEvent event) {
        log.warn("🔥 RECEIVED FROM KAFKA (DELIVERED): {}", event);
    }

    @KafkaListener(
            topics = "saga.events.order.paid",
            groupId = "order-test",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenPaid(OrderPaidEvent event) {
        log.warn("🔥 RECEIVED FROM KAFKA (PAID): {}", event);
    }

    @KafkaListener(
            topics = "saga.events.order.cancelled",
            groupId = "order-test",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenCancelled(OrderCancelledEvent event) {
        log.warn("🔥 RECEIVED FROM KAFKA (CANCELLED): {}", event);
    }
}
