package com.project.persistence;

import com.project.saga.model.SagaStatus;
import com.project.saga.model.SagaStepStatus;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "saga_state")
public class SagaState {

    @Id
    private String sagaId;

    private String orderId;

    @Enumerated(EnumType.STRING)
    private SagaStatus status;

    @Enumerated(EnumType.STRING)
    private SagaStepStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private SagaStepStatus inventoryStatus;

    @Enumerated(EnumType.STRING)
    private SagaStepStatus shippingStatus;

    private Instant createdAt;
    private Instant updatedAt;

    public SagaState() {}

    public SagaState(String sagaId, String orderId) {
        this.sagaId = sagaId;
        this.orderId = orderId;
        this.status = SagaStatus.RUNNING;
        this.paymentStatus = SagaStepStatus.PENDING;
        this.inventoryStatus = SagaStepStatus.PENDING;
        this.shippingStatus = SagaStepStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void touch() {
        this.updatedAt = Instant.now();
    }

    // --- Getters & Setters ---
    public String getSagaId() { return sagaId; }
    public void setSagaId(String sagaId) { this.sagaId = sagaId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public SagaStatus getStatus() { return status; }
    public void setStatus(SagaStatus status) { this.status = status; }

    public SagaStepStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(SagaStepStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public SagaStepStatus getInventoryStatus() { return inventoryStatus; }
    public void setInventoryStatus(SagaStepStatus inventoryStatus) { this.inventoryStatus = inventoryStatus; }

    public SagaStepStatus getShippingStatus() { return shippingStatus; }
    public void setShippingStatus(SagaStepStatus shippingStatus) { this.shippingStatus = shippingStatus; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}

