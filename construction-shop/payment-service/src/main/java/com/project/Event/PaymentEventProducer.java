package com.project.Event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderPaidEvent(String orderJson) {
        kafkaTemplate.send("order-paid", orderJson);
        System.out.println("Отправлено событие order-paid: " + orderJson);
    }
}