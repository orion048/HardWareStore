package com.project.Controller;

import com.project.Repository.SagaStateRepository;
import com.project.service.SagaCoordinatorService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/saga")
public class SagaController {

    private final SagaCoordinatorService service;
    private final SagaStateRepository repo;

    public SagaController(SagaCoordinatorService service, SagaStateRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @PostMapping("/start")
    public String start(@RequestParam String orderId,
                        @RequestParam BigDecimal amount,
                        @RequestParam String currency,
                        @RequestParam String customerId,
                        @RequestParam String address) {
        String sagaId = UUID.randomUUID().toString();
        List<OrderItem> items = List.of(new OrderItem("SKU-123", 2)); // пример данных

        OrderSagaStart start = new OrderSagaStart(sagaId, orderId, amount, currency, items, customerId, address);
        service.start(start);
        return sagaId;
    }

    @GetMapping("/state/{sagaId}")
    public SagaState state(@PathVariable String sagaId) {
        return repo.findById(sagaId).orElse(null);
    }
}
