package com.project.service;

import com.hardwarestore.common.events.BaseEvent;
import com.hardwarestore.common.events.PaymentCompletedEvent;
import com.hardwarestore.common.events.PaymentFailedEvent;
import com.hardwarestore.common.events.ProcessPaymentCommand;
import com.project.model.Payment;
import com.project.model.PaymentStatus;
import com.project.repository.PaymentRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    public PaymentService(PaymentRepository paymentRepository,
                          KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.paymentRepository = paymentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processPayment(ProcessPaymentCommand command) {

        // 1. Создаём платеж
        Payment payment = new Payment();
        payment.setOrderId(command.getOrderId());
        payment.setAmount(command.getAmount());
        payment.setStatus(PaymentStatus.PENDING);
        payment = paymentRepository.save(payment);

        // 2. Выполняем бизнес-логику
        boolean success = payment.getAmount().compareTo(BigDecimal.ZERO) > 0;
        // пример

        if (success) {
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            // 3. Отправляем событие об успешной оплате
            kafkaTemplate.send("saga.events.payment",
                    new PaymentCompletedEvent(
                            command.getOrderId(),
                            payment.getId()
                    )
            );

        } else {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);

            // 4. Отправляем событие о провале оплаты
            kafkaTemplate.send("saga.events.payment.failed",
                    new PaymentFailedEvent(
                            command.getOrderId(),
                            payment.getId(),
                            "Payment failed"
                    )
            );
        }
    }

    public Payment updateStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByOrder(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}

