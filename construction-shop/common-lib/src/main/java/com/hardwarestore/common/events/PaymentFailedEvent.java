package com.hardwarestore.common.events;

public class PaymentFailedEvent extends BaseEvent {

    private final Long orderId;
    private final Long paymentId;
    private final String reason;

    public PaymentFailedEvent(Long orderId, Long paymentId, String reason) {
        super();
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.reason = reason;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public String getReason() {
        return reason;
    }
}