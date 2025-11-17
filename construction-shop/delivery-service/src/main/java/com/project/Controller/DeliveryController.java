package com.project.Controller;

import com.project.Model.Delivery;
import com.project.Service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<Delivery> startDelivery(@RequestParam Long orderId) {
        return ResponseEntity.ok(deliveryService.startDelivery(orderId));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Delivery> completeDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.completeDelivery(id));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Delivery service is running");
    }
}