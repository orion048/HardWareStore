package com.project.controller.internal;

import com.hardwarestore.common.delivery.DeliveryStatus;
import com.hardwarestore.common.dto.delivery.DeliveryRequest;
import com.hardwarestore.common.dto.delivery.DeliveryResponse;
import com.project.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/deliveries")
public class InternalDeliveryController {

    private final DeliveryService deliveryService;

    public InternalDeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> create(@Valid @RequestBody DeliveryRequest request) {
        return ResponseEntity.ok(deliveryService.createDelivery(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam DeliveryStatus status
    ) {
        return ResponseEntity.ok(deliveryService.updateStatus(id, status));
    }
}

