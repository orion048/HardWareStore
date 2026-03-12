package com.hardwarestore.common.events;

public class OrderCompletedEvent extends BaseEvent {
    private final Long orderId;

    public OrderCompletedEvent(Long orderId) {
        super();
        this.orderId = orderId;
    }

    public Long getOrderId() { return orderId; }
}

