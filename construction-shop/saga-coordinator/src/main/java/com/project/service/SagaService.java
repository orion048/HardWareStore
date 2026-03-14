package com.project.service;


import com.hardwarestore.common.events.*;
import com.hardwarestore.common.events.DeliveryCreatedEvent;
import com.project.model.SagaInstance;
import com.project.model.SagaState;
import com.project.producer.SagaCommandProducer;
import com.project.repository.SagaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SagaService {

    private final SagaRepository sagaRepository;
    private final SagaCommandProducer commandProducer;

    public SagaService(SagaRepository sagaRepository, SagaCommandProducer commandProducer) {
        this.sagaRepository = sagaRepository;
        this.commandProducer = commandProducer;
    }

    public void handleOrderCreated(OrderCreatedEvent event) {
        SagaInstance saga = new SagaInstance();
        saga.setOrderId(event.getOrderId());
        saga.setUserId(event.getUserId());
        saga.setState(SagaState.AWAITING_PAYMENT);
        saga.setCreatedAt(Instant.now());
        saga.setUpdatedAt(Instant.now());
        sagaRepository.save(saga);

        // отправляем команду в payment-service
        ProcessPaymentCommand cmd = new ProcessPaymentCommand(
                event.getOrderId(),
                event.getTotalAmount()
        );
        commandProducer.sendToPayment(cmd);
    }

    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        SagaInstance saga = sagaRepository.findByOrderId(event.getOrderId());
        if (saga == null) return;

        saga.setState(SagaState.AWAITING_DELIVERY);
        saga.setUpdatedAt(Instant.now());
        sagaRepository.save(saga);

        CreateDeliveryCommand cmd = new CreateDeliveryCommand(
                event.getOrderId(),
                saga.getUserId()
        );
        commandProducer.sendToDelivery(cmd);
    }

    public void handlePaymentFailed(PaymentFailedEvent event) {
        SagaInstance saga = sagaRepository.findByOrderId(event.getOrderId());
        if (saga == null) return;

        saga.setState(SagaState.PAYMENT_FAILED);
        saga.setUpdatedAt(Instant.now());
        sagaRepository.save(saga);

        // компенсация — отменяем заказ
        OrderCancelledEvent cancel = new OrderCancelledEvent(
                event.getOrderId(),
                event.getReason()
        );
        commandProducer.sendToOrder(cancel);
    }

    public void handleDeliveryCreated(DeliveryCreatedEvent event) {
        SagaInstance saga = sagaRepository.findByOrderId(event.getOrderId());
        if (saga == null) return;

        saga.setState(SagaState.COMPLETED);
        saga.setUpdatedAt(Instant.now());
        sagaRepository.save(saga);

        // можно отправить OrderCompletedEvent или просто обновить заказ через order-service
        OrderCompletedEvent completed = new OrderCompletedEvent(event.getOrderId());
        commandProducer.sendToOrder(completed);
    }
}

