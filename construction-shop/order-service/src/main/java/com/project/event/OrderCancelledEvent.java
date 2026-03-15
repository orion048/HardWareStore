package com.project.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderCancelledEvent {
    private Long orderId;
    private String reason;

    public OrderCancelledEvent(Long orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }
}
