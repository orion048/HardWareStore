package com.hardwarestore.common.events;

import java.math.BigDecimal;

public class ProcessPaymentCommand extends BaseEvent {
    private final Long orderId;
    private final BigDecimal amount;

    public ProcessPaymentCommand(Long orderId, BigDecimal amount) {
        super();
        this.orderId = orderId;
        this.amount = amount;
    }

    public Long getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
}

