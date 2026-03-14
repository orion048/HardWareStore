package com.project.producer;

import com.project.event.OrderCancelledEvent;
import com.project.event.OrderCreatedEvent;
import com.project.event.OrderDeliveredEvent;
import com.project.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderCreated(OrderCreatedEvent event) {
        log.info("Sending OrderCreatedEvent: {}", event);
        kafkaTemplate.send("saga.events.order", event);
    }

    public void sendOrderCancelled(OrderCancelledEvent event) {
        log.info("Sending OrderCancelledEvent: {}", event);
        kafkaTemplate.send("saga.events.order.cancelled", event);
    }

    public void sendOrderPaid(OrderPaidEvent event) {
        log.info("Sending OrderPaidEvent: {}", event);
        kafkaTemplate.send("saga.events.order.paid", event);
    }

    public void sendOrderDelivered(OrderDeliveredEvent event) {
        log.info("Sending OrderDeliveredEvent: {}", event);
        kafkaTemplate.send("saga.events.order.delivered", event);
    }

}

