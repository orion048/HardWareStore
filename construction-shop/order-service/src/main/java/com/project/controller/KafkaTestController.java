package com.project.controller;

import com.project.event.OrderCancelledEvent;
import com.project.event.OrderDeliveredEvent;
import com.project.event.OrderPaidEvent;
import com.project.producer.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaTestController {

    private final OrderEventProducer producer;

    @GetMapping("/test/kafka/delivered/{id}")
    public String testDelivered(@PathVariable Long id) {
        producer.sendOrderDelivered(new OrderDeliveredEvent(id));
        return "Delivered event sent for order " + id;
    }

    @GetMapping("/test/kafka/paid/{id}")
    public String testPaid(@PathVariable Long id) {
        producer.sendOrderPaid(new OrderPaidEvent(id, 999L));
        return "Paid event sent for order " + id;
    }

    @GetMapping("/test/kafka/cancel/{id}")
    public String testCancel(@PathVariable Long id) {
        producer.sendOrderCancelled(new OrderCancelledEvent(id, "Test reason"));
        return "Cancel event sent for order " + id;
    }
}

