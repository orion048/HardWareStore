package com.project.producer;

import com.hardwarestore.common.events.BaseEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SagaCommandProducer {

    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    public SagaCommandProducer(KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToPayment(BaseEvent command) {
        kafkaTemplate.send("saga.commands.payment", command.getEventId(), command);
    }

    public void sendToDelivery(BaseEvent command) {
        kafkaTemplate.send("saga.commands.delivery", command.getEventId(), command);
    }

    public void sendToOrder(BaseEvent command) {
        kafkaTemplate.send("saga.commands.order", command.getEventId(), command);
    }
}

