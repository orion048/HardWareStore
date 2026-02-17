package com.project.core;


import com.project.model.*;
import com.project.persistence.SagaState;
import com.project.persistence.SagaStateRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SagaCoordinatorService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SagaStateRepository sagaRepo;

    public SagaCoordinatorService(KafkaTemplate<String, Object> kafkaTemplate,
                                  SagaStateRepository sagaRepo) {
        this.kafkaTemplate = kafkaTemplate;
        this.sagaRepo = sagaRepo;
    }

    /**
     * Запуск новой саги: создаём SagaState и инициируем оплату.
     */
    @Transactional
    public void startSaga(OrderSagaStart start) {
        SagaState state = new SagaState(start.getSagaId(), start.getOrderId());
        sagaRepo.save(state);

        PaymentCommand cmd = new PaymentCommand(
                start.getSagaId(),
                start.getOrderId(),
                start.getCustomerId(),
                start.getAmount(),
                start.getCurrency()
        );
        kafkaTemplate.send("payment-debit-command", start.getOrderId(), cmd);
    }

    /**
     * Обработка результата оплаты.
     */
    @Transactional
    public void onPaymentResult(PaymentResult result) {
        SagaState state = sagaRepo.findById(result.getSagaId())
                .orElseThrow(() -> new IllegalStateException("Saga not found: " + result.getSagaId()));

        if (result.isSuccess()) {
            state.setPaymentStatus(SagaStepStatus.SUCCESS);
            sagaRepo.save(state);

            // Следующий шаг: резервирование товара
            InventoryCommand cmd = new InventoryCommand(state.getSagaId(), state.getOrderId(), null);
            kafkaTemplate.send("inventory-reserve-command", state.getOrderId(), cmd);
        } else {
            state.setPaymentStatus(SagaStepStatus.FAILED);
            state.setStatus(SagaStatus.FAILED);
            sagaRepo.save(state);

            // Компенсация: отмена заказа
            kafkaTemplate.send("order-cancel-command", state.getOrderId(),
                    "Cancel order due to payment failure: " + result.getReason());
        }
    }

    /**
     * Обработка результата резервирования товара.
     */
    @Transactional
    public void onInventoryResult(InventoryResult result, OrderSagaStart original) {
        SagaState state = sagaRepo.findById(result.getSagaId())
                .orElseThrow(() -> new IllegalStateException("Saga not found: " + result.getSagaId()));

        if (result.isSuccess()) {
            state.setInventoryStatus(SagaStepStatus.SUCCESS);
            sagaRepo.save(state);

            // Следующий шаг: доставка
            ShippingCommand shippingCmd = new ShippingCommand(state.getSagaId(), state.getOrderId(), original.getAddress());
            kafkaTemplate.send("shipping-init-command", state.getOrderId(), shippingCmd);
        } else {
            state.setInventoryStatus(SagaStepStatus.FAILED);
            state.setStatus(SagaStatus.COMPENSATING);
            sagaRepo.save(state);

            // Компенсация: возврат денег и отмена заказа
            kafkaTemplate.send("payment-rollback-command", state.getOrderId(),
                    "Rollback payment due to inventory failure: " + result.getReason());
            kafkaTemplate.send("order-cancel-command", state.getOrderId(),
                    "Cancel order due to inventory failure: " + result.getReason());
        }
    }

    /**
     * Обработка результата доставки.
     */
    @Transactional
    public void onShippingResult(ShippingResult result) {
        SagaState state = sagaRepo.findById(result.getSagaId())
                .orElseThrow(() -> new IllegalStateException("Saga not found: " + result.getSagaId()));

        if (result.isSuccess()) {
            state.setShippingStatus(SagaStepStatus.SUCCESS);
            state.setStatus(SagaStatus.COMPLETED);
            sagaRepo.save(state);
        } else {
            state.setShippingStatus(SagaStepStatus.FAILED);
            state.setStatus(SagaStatus.COMPENSATING);
            sagaRepo.save(state);

            // Компенсация: освободить товар, вернуть деньги, отменить заказ
            kafkaTemplate.send("inventory-release-command", state.getOrderId(),
                    "Release inventory due to shipping failure: " + result.getReason());
            kafkaTemplate.send("payment-rollback-command", state.getOrderId(),
                    "Rollback payment due to shipping failure: " + result.getReason());
            kafkaTemplate.send("order-cancel-command", state.getOrderId(),
                    "Cancel order due to shipping failure: " + result.getReason());

            state.setStatus(SagaStatus.COMPENSATED);
            sagaRepo.save(state);
        }
    }
}
