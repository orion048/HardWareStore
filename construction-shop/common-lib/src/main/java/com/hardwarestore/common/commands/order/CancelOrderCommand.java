package com.hardwarestore.common.commands.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CancelOrderCommand {
    private Long orderId;
    private String reason;

    public CancelOrderCommand() {
    }

    public CancelOrderCommand(Long orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
