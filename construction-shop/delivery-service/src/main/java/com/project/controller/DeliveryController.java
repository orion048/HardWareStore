package com.project.controller;

import com.hardwarestore.common.delivery.DeliveryStatus;
import com.hardwarestore.common.dto.delivery.DeliveryRequest;
import com.hardwarestore.common.dto.delivery.DeliveryResponse;
import com.project.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> create(@Valid @RequestBody DeliveryRequest request) {
        return ResponseEntity.ok(deliveryService.createDelivery(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeliveryResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(deliveryService.getByUser(userId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<DeliveryResponse>> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(deliveryService.getByOrder(orderId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam DeliveryStatus status
    ) {
        return ResponseEntity.ok(deliveryService.updateStatus(id, status));
    }
}

