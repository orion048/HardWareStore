package com.project.Client;

import com.project.DTO.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service")
public interface PaymentClient {
    @PostMapping("/payments")
    PaymentDto processPayment(@RequestParam("orderId") Long orderId,
                              @RequestParam("amount") double amount);
}
