package com.project.Model;

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
    public void touch() { this.updatedAt = Instant.now(); }

    // getters/setters omitted for brevity
    // ...
}
