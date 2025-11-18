package com.project.Messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.service.SagaCoordinatorService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SagaListeners {

    private final SagaCoordinatorService service;
    private final ObjectMapper mapper;

    public SagaListeners(SagaCoordinatorService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "payment-result", groupId = "saga-coordinator")
    public void onPaymentResult(String payload) throws Exception {
        PaymentResult result = mapper.readValue(payload, PaymentResult.class);
        service.onPaymentResult(result);
    }

    @KafkaListener(topics = "inventory-result", groupId = "saga-coordinator")
    public void onInventoryResult(String payload) throws Exception {
        InventoryResult result = mapper.readValue(payload, InventoryResult.class);
        // В реальном коде получите оригинальные данные (например, из БД/кэша)
        OrderSagaStart original = new OrderSagaStart(result.sagaId(), result.orderId(), null, null, java.util.List.of(), null, null);
        service.onInventoryResult(result, original);
    }

    @KafkaListener(topics = "shipping-result", groupId = "saga-coordinator")
    public void onShippingResult(String payload) throws Exception {
        ShippingResult result = mapper.readValue(payload, ShippingResult.class);
        service.onShippingResult(result);
    }
}
