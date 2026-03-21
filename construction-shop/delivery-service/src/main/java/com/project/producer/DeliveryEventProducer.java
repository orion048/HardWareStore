package com.project.producer;


import com.hardwarestore.common.events.OrderDeliveredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderDelivered(OrderDeliveredEvent event) {
        log.info("Sending OrderDeliveredEvent: {}", event);
        kafkaTemplate.send("saga.events.order.delivered", event);
    }
}

