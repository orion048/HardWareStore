package com.hardwarestore.common.order;

public enum OrderStatus {
    PENDING,
    AWAITING_PAYMENT,
    PAID,
    PAYMENT_FAILED,
    AWAITING_DELIVERY,
    DELIVERING,
    DELIVERED,
    CANCELLED
}