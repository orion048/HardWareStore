package com.hardwarestore.common.events;

import java.math.BigDecimal;

public class OrderCreatedEvent extends BaseEvent {
    private final Long orderId;
    private final Long userId;
    private final BigDecimal totalAmount;

    public OrderCreatedEvent(Long orderId, Long userId, BigDecimal totalAmount) {
        super();
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    public Long getOrderId() { return orderId; }
    public Long getUserId() { return userId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
