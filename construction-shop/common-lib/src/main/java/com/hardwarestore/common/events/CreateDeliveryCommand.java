package com.hardwarestore.common.events;

public class CreateDeliveryCommand extends BaseEvent {
    private final Long orderId;
    private final Long userId;


    public CreateDeliveryCommand(Long orderId, Long userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public Long getOrderId() { return orderId; }
    public Long getUserId() { return userId; }
}

