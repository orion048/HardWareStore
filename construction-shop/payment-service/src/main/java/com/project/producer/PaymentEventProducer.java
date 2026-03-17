package com.project.producer;

import com.hardwarestore.common.events.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentCompleted(PaymentCompletedEvent event) {
        log.info("Sending PaymentCompletedEvent: {}", event);
        kafkaTemplate.send("saga.events.payment.completed", event);
    }
}
