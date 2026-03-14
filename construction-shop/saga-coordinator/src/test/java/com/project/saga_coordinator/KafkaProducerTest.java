package com.project.saga_coordinator;

import com.hardwarestore.common.events.BaseEvent;
import com.hardwarestore.common.events.OrderCreatedEvent;
import com.project.producer.SagaCommandProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;


@SpringBootTest
class KafkaProducerTest {

    @Autowired
    private SagaCommandProducer producer;

    @Test
    void testSendEventToKafka() {
        OrderCreatedEvent event = new OrderCreatedEvent(
                1L,                // orderId (Long)
                10L,               // userId (Long)
                BigDecimal.valueOf(100) // totalAmount (BigDecimal)
        );

        producer.sendToOrder(event);
    }
}
