package com.project.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Клиент для Delivery Service
@FeignClient(name = "delivery-service")
public interface DeliveryClient {
    @PostMapping("/deliveries")
    String startDelivery(@RequestParam("orderId") Long orderId);
}