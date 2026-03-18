package com.hardwarestore.common.commands.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ApproveOrderCommand {
    private Long orderId;

    public ApproveOrderCommand() {
    }

    public ApproveOrderCommand(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
