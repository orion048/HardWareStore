package com.hardwarestore.common.commands.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CreateOrderCommand {
    private Long orderId;
    private Long userId;
    private Double amount;

    public CreateOrderCommand() {
    }

    public CreateOrderCommand(Long orderId, Long userId, Double amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
