package com.project.listener;

import com.hardwarestore.common.events.ProcessPaymentCommand;
import com.project.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCommandListener {

    private final PaymentService paymentService;

    public PaymentCommandListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "saga.commands.payment", groupId = "payment-service")
    public void onProcessPayment(ProcessPaymentCommand command) {
        paymentService.processPayment(command);
    }
}
