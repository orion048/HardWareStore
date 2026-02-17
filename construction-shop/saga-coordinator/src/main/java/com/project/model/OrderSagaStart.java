package com.project.model;

import java.math.BigDecimal;
import java.util.List;

public class OrderSagaStart {
    private String sagaId;
    private String orderId;
    private BigDecimal amount;
    private String currency;
    private List<OrderItem> items;
    private String customerId;
    private String address;

    public OrderSagaStart() {}

    public OrderSagaStart(String sagaId, String orderId, BigDecimal amount,
                          String currency, List<OrderItem> items,
                          String customerId, String address) {
        this.sagaId = sagaId;
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.items = items;
        this.customerId = customerId;
        this.address = address;
    }

    // Getters & Setters
    public String getSagaId() { return sagaId; }
    public void setSagaId(String sagaId) { this.sagaId = sagaId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
