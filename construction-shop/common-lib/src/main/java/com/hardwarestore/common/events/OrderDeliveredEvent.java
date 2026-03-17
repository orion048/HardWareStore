package com.hardwarestore.common.events;

public class OrderDeliveredEvent extends BaseEvent {
    private final Long orderId;

    public OrderDeliveredEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
