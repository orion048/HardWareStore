package com.project;

import com.hardwarestore.common.events.BaseEvent;
import com.project.producer.SagaCommandProducer;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootTest
class KafkaProducerTest {

    @Autowired
    private SagaCommandProducer producer;

    @Test
    void testSendEventToKafka() {
        BaseEvent event = new BaseEvent("test-event-id");
        producer.sendToOrder(event);
    }
}
