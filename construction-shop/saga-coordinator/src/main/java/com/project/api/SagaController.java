package com.project.api;


import com.project.core.SagaCoordinatorService;
import com.project.model.OrderItem;
import com.project.model.OrderSagaStart;
import com.project.persistence.SagaState;
import com.project.persistence.SagaStateRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/saga")
public class SagaController {

    private final SagaCoordinatorService sagaService;
    private final SagaStateRepository sagaRepo;

    public SagaController(SagaCoordinatorService sagaService, SagaStateRepository sagaRepo) {
        this.sagaService = sagaService;
        this.sagaRepo = sagaRepo;
    }

    /**
     * Запуск новой саги.
     * Пример запроса:
     * POST /saga/start?orderId=ORD-1&amount=100&currency=USD&customerId=CUST-1&address=Oslo
     */
    @PostMapping("/start")
    public String startSaga(@RequestParam String orderId,
                            @RequestParam BigDecimal amount,
                            @RequestParam String currency,
                            @RequestParam String customerId,
                            @RequestParam String address) {
        String sagaId = UUID.randomUUID().toString();

        // Для примера добавляем один товар в заказ
        List<OrderItem> items = List.of(new OrderItem("SKU-123", 2));

        OrderSagaStart start = new OrderSagaStart(
                sagaId,
                orderId,
                amount,
                currency,
                items,
                customerId,
                address
        );

        sagaService.startSaga(start);
        return "Saga started with ID: " + sagaId;
    }

    /**
     * Проверка состояния саги по её ID.
     * Пример запроса:
     * GET /saga/state/{sagaId}
     */
    @GetMapping("/state/{sagaId}")
    public SagaState getSagaState(@PathVariable String sagaId) {
        return sagaRepo.findById(sagaId).orElse(null);
    }

    /**
     * Проверка состояния саги по ID заказа.
     * Пример запроса:
     * GET /saga/state/order/{orderId}
     */
    @GetMapping("/state/order/{orderId}")
    public SagaState getSagaStateByOrder(@PathVariable String orderId) {
        return sagaRepo.findByOrderId(orderId).orElse(null);
    }
}

