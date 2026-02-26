package com.hardwarestore.common.events;
import java.util.UUID;
import com.hardwarestore.common.events.BaseEvent;

public class DeliveryCreatedEvent extends BaseEvent {

    private final Long orderId;
    private final Long deliveryId;

    public DeliveryCreatedEvent(Long orderId, Long deliveryId) {
        super();
        this.orderId = orderId;
        this.deliveryId = deliveryId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }
}