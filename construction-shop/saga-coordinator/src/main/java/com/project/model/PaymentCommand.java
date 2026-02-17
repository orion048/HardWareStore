package com.project.model;

import java.math.BigDecimal;

public class PaymentCommand {
    private String sagaId;
    private String orderId;
    private String customerId;
    private BigDecimal amount;
    private String currency;

    public PaymentCommand() {}

    public PaymentCommand(String sagaId, String orderId, String customerId,
                          BigDecimal amount, String currency) {
        this.sagaId = sagaId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
    }

    // Getters & Setters
    public String getSagaId() { return sagaId; }
    public void setSagaId(String sagaId) { this.sagaId = sagaId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
