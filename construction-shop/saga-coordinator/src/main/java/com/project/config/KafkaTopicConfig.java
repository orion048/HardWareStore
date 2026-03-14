package com.project.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderEventsTopic() {
        return new NewTopic("saga.events.order", 1, (short) 1);
    }

    @Bean
    public NewTopic paymentEventsTopic() {
        return new NewTopic("saga.events.payment", 1, (short) 1);
    }

    @Bean
    public NewTopic paymentFailedEventsTopic() {
        return new NewTopic("saga.events.payment.failed", 1, (short) 1);
    }

    @Bean
    public NewTopic deliveryEventsTopic() {
        return new NewTopic("saga.events.delivery", 1, (short) 1);
    }

    @Bean
    public NewTopic paymentCommandsTopic() {
        return new NewTopic("saga.commands.payment", 1, (short) 1);
    }

    @Bean
    public NewTopic deliveryCommandsTopic() {
        return new NewTopic("saga.commands.delivery", 1, (short) 1);
    }

    @Bean
    public NewTopic orderCommandsTopic() {
        return new NewTopic("saga.commands.order", 1, (short) 1);
    }
}
