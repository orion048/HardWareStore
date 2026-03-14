package com.project.listener;

import com.hardwarestore.common.events.*;
import com.project.service.SagaService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SagaEventListener {

    private final SagaService sagaService;

    public SagaEventListener(SagaService sagaService) {
        this.sagaService = sagaService;
    }

    @KafkaListener(topics = "saga.events.order", groupId = "saga-coordinator")
    public void onOrderCreated(OrderCreatedEvent event) {
        sagaService.handleOrderCreated(event);
    }

    @KafkaListener(topics = "saga.events.payment", groupId = "saga-coordinator")
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        sagaService.handlePaymentCompleted(event);
    }

    @KafkaListener(topics = "saga.events.payment.failed", groupId = "saga-coordinator")
    public void onPaymentFailed(PaymentFailedEvent event) {
        sagaService.handlePaymentFailed(event);
    }

    @KafkaListener(topics = "saga.events.delivery", groupId = "saga-coordinator")
    public void onDeliveryCreated(DeliveryCreatedEvent event) {
        sagaService.handleDeliveryCreated(event);
    }
}
