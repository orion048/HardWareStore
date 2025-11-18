package com.project.service;

import com.project.Model.SagaState;
import com.project.Repository.SagaStateRepository;
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

    @Transactional
    public void start(OrderSagaStart start) {
        SagaState state = sagaRepo.save(new SagaState(start.sagaId(), start.orderId()));

        PaymentCommand cmd = new PaymentCommand(
                start.sagaId(), start.orderId(), start.customerId(),
                start.amount(), start.currency()
        );
        kafkaTemplate.send("payment-debit-command", start.orderId(), cmd);
    }

    @Transactional
    public void onPaymentResult(PaymentResult result) {
        SagaState state = sagaRepo.findById(result.sagaId())
                .orElseThrow(() -> new IllegalStateException("Saga not found: " + result.sagaId()));

        if (result.success()) {
            state.setPaymentStatus(SagaStepStatus.SUCCESS);
            sagaRepo.save(state);

            // next step: inventory
            InventoryCommand cmd = new InventoryCommand(state.getSagaId(), state.getOrderId(), null /* pass actual items */);
            kafkaTemplate.send("inventory-reserve-command", state.getOrderId(), cmd);
        } else {
            state.setPaymentStatus(SagaStepStatus.FAILED);
            state.setStatus(SagaStatus.FAILED);
            sagaRepo.save(state);

            // compensate: cancel order
            CompensationCommand cancelOrder = new CompensationCommand(state.getSagaId(), state.getOrderId(), "ORDER_CANCEL", result.reason());
            kafkaTemplate.send("order-cancel-command", state.getOrderId(), cancelOrder);
        }
    }

    @Transactional
    public void onInventoryResult(InventoryResult result, OrderSagaStart original) {
        SagaState state = sagaRepo.findById(result.sagaId())
                .orElseThrow(() -> new IllegalStateException("Saga not found: " + result.sagaId()));

        if (result.success()) {
            state.setInventoryStatus(SagaStepStatus.SUCCESS);
            sagaRepo.save(state);

            ShippingCommand shippingCmd = new ShippingCommand(state.getSagaId(), state.getOrderId(), original.address());
            kafkaTemplate.send("shipping-init-command", state.getOrderId(), shippingCmd);
        } else {
            state.setInventoryStatus(SagaStepStatus.FAILED);
            state.setStatus(SagaStatus.COMPENSATING);
            sagaRepo.save(state);

            // compensate: payment rollback
            CompensationCommand paymentRollback = new CompensationCommand(state.getSagaId(), state.getOrderId(), "PAYMENT_ROLLBACK", result.reason());
            kafkaTemplate.send("payment-rollback-command", state.getOrderId(), paymentRollback);

            // compensate: cancel order
            CompensationCommand cancelOrder = new CompensationCommand(state.getSagaId(), state.getOrderId(), "ORDER_CANCEL", result.reason());
            kafkaTemplate.send("order-cancel-command", state.getOrderId(), cancelOrder);
        }
    }

    @Transactional
    public void onShippingResult(ShippingResult result) {
        SagaState state = sagaRepo.findById(result.sagaId())
                .orElseThrow(() -> new IllegalStateException("Saga not found: " + result.sagaId()));

        if (result.success()) {
            state.setShippingStatus(SagaStepStatus.SUCCESS);
            state.setStatus(SagaStatus.COMPLETED);
            sagaRepo.save(state);
        } else {
            state.setShippingStatus(SagaStepStatus.FAILED);
            state.setStatus(SagaStatus.COMPENSATING);
            sagaRepo.save(state);

            // compensate: release inventory
            CompensationCommand releaseInventory = new CompensationCommand(state.getSagaId(), state.getOrderId(), "INVENTORY_RELEASE", result.reason());
            kafkaTemplate.send("inventory-release-command", state.getOrderId(), releaseInventory);

            // compensate: payment rollback (if needed)
            CompensationCommand paymentRollback = new CompensationCommand(state.getSagaId(), state.getOrderId(), "PAYMENT_ROLLBACK", result.reason());
            kafkaTemplate.send("payment-rollback-command", state.getOrderId(), paymentRollback);

            // compensate: cancel order
            CompensationCommand cancelOrder = new CompensationCommand(state.getSagaId(), state.getOrderId(), "ORDER_CANCEL", result.reason());
            kafkaTemplate.send("order-cancel-command", state.getOrderId(), cancelOrder);

            state.setStatus(SagaStatus.COMPENSATED);
            sagaRepo.save(state);
        }
    }
}

