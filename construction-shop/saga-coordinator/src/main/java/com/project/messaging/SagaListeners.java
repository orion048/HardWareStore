package com.project.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.core.SagaCoordinatorService;
import com.project.model.InventoryResult;
import com.project.model.OrderSagaStart;
import com.project.model.PaymentResult;
import com.project.model.ShippingResult;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SagaListeners {

    private final SagaCoordinatorService sagaService;
    private final ObjectMapper mapper;

    public SagaListeners(SagaCoordinatorService sagaService, ObjectMapper mapper) {
        this.sagaService = sagaService;
        this.mapper = mapper;
    }

    /**
     * Слушаем результат оплаты
     */
    @KafkaListener(topics = "payment-result", groupId = "saga-coordinator")
    public void onPaymentResult(String payload) throws Exception {
        PaymentResult result = mapper.readValue(payload, PaymentResult.class);
        sagaService.onPaymentResult(result);
    }

    /**
     * Слушаем результат резервирования товара
     */
    @KafkaListener(topics = "inventory-result", groupId = "saga-coordinator")
    public void onInventoryResult(String payload) throws Exception {
        InventoryResult result = mapper.readValue(payload, InventoryResult.class);

        // В реальном проекте лучше подтягивать оригинальные данные заказа из БД
        OrderSagaStart original = new OrderSagaStart(
                result.getSagaId(),
                result.getOrderId(),
                null, // amount
                null, // currency
                null, // items
                null, // customerId
                null  // address
        );

        sagaService.onInventoryResult(result, original);
    }

    /**
     * Слушаем результат доставки
     */
    @KafkaListener(topics = "shipping-result", groupId = "saga-coordinator")
    public void onShippingResult(String payload) throws Exception {
        ShippingResult result = mapper.readValue(payload, ShippingResult.class);
        sagaService.onShippingResult(result);
    }
}

