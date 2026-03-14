package com.project.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPaidEvent {
    private Long orderId;
    private Long paymentId;

    public OrderPaidEvent(Long orderId) {
        this.orderId = orderId;
    }
}
