package com.project.controller;

import com.project.model.Payment;
import com.project.model.PaymentStatus;
import com.project.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestParam Long orderId,
                                                 @RequestParam Double amount) {
        return ResponseEntity.ok(paymentService.createPayment(orderId, amount));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Payment> updateStatus(@PathVariable Long id,
                                                @RequestParam PaymentStatus status) {
        return ResponseEntity.ok(paymentService.updateStatus(id, status));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Payment>> getPaymentsByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentsByOrder(orderId));
    }
}
