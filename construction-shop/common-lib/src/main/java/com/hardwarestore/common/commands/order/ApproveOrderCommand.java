package com.hardwarestore.common.commands.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ApproveOrderCommand {
    private Long orderId;

    private Long paymentId;

    public ApproveOrderCommand() {
    }

    public ApproveOrderCommand(Long paymentId, Long orderId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
