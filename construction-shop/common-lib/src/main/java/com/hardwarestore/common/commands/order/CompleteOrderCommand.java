package com.hardwarestore.common.commands.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CompleteOrderCommand {
    private Long orderId;

    public CompleteOrderCommand() {
    }

    public CompleteOrderCommand(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
