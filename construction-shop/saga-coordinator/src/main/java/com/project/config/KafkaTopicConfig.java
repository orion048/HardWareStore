package com.project.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic paymentEventsTopic() {
        return new NewTopic("saga.events.payment", 1, (short) 1);
    }

    @Bean
    public NewTopic paymentFailedEventsTopic() {
        return new NewTopic("saga.events.payment.failed", 1, (short) 1);
    }

    @Bean
    public NewTopic paymentCompletedEventsTopic() {
        return new NewTopic("saga.events.payment.completed", 1, (short) 1);
    }

    @Bean
    public NewTopic paymentCommandsTopic() {
        return new NewTopic("saga.commands.payment", 1, (short) 1);
    }


    @Bean
    public NewTopic orderDeliveredEventsTopic() {
        return new NewTopic("saga.events.order.delivered", 1, (short) 1);
    }

    @Bean
    public NewTopic deliveryStartCommandsTopic() {
        return new NewTopic("saga.commands.delivery.start", 1, (short) 1);
    }

    @Bean
    public NewTopic orderCommandsTopic() {
        return new NewTopic("saga.commands.order", 1, (short) 1);
    }

    @Bean
    public NewTopic orderEventsTopic() {
        return new NewTopic("saga.events.order", 1, (short) 1);
    }

    @Bean
    public NewTopic orderEventsCancelledTopic() {
        return new NewTopic("saga.events.order.cancelled", 1, (short) 1);
    }

    @Bean
    public NewTopic orderEventsPaidTopic() {
        return new NewTopic("saga.events.order.paid", 1, (short) 1);
    }

    @Bean
    public NewTopic orderEventsDeliveredTopic() {
        return new NewTopic("saga.events.order.delivered", 1, (short) 1);
    }

    @Bean
    public NewTopic orderCommandsCancelTopic() {
        return new NewTopic("saga.commands.order.cancel", 1, (short) 1);
    }

    @Bean
    public NewTopic orderCommandsApproveTopic() {
        return new NewTopic("saga.commands.order.approve", 1, (short) 1);
    }

    @Bean
    public NewTopic orderCommandsCompleteTopic() {
        return new NewTopic("saga.commands.order.complete", 1, (short) 1);
    }


}
