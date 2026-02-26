package com.hardwarestore.common.events;

public class OrderCancelledEvent extends BaseEvent {

    private final Long orderId;
    private final String reason;

    public OrderCancelledEvent(Long orderId, String reason) {
        super();
        this.orderId = orderId;
        this.reason = reason;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getReason() {
        return reason;
    }
}