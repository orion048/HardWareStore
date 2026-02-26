package com.hardwarestore.common.events;

public class PaymentCompletedEvent extends BaseEvent {

    private final Long orderId;
    private final Long paymentId;

    public PaymentCompletedEvent(Long orderId, Long paymentId) {
        super();
        this.orderId = orderId;
        this.paymentId = paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }
}