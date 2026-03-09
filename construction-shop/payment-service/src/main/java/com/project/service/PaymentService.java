package com.project.Service;

import com.project.Event.PaymentEventProducer;
import com.project.Model.Payment;
import com.project.Reposintory.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer eventProducer;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentEventProducer eventProducer) {
        this.paymentRepository = paymentRepository;
        this.eventProducer = eventProducer;
    }

    public Payment processPayment(Long orderId, double amount) {
        Payment payment = new Payment(orderId, amount, "SUCCESS");
        Payment saved = paymentRepository.save(payment);

        // Формируем JSON события
        String eventPayload = "{ \"orderId\": " + saved.getOrderId() +
                ", \"paymentId\": " + saved.getId() +
                ", \"status\": \"" + saved.getStatus() + "\" }";

        // Отправляем событие в Kafka
        eventProducer.sendOrderPaidEvent(eventPayload);

        return saved;
    }
}
