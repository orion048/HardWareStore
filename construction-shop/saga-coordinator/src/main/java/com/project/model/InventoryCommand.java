package com.project.model;

import java.util.List;

public class InventoryCommand {
    private String sagaId;
    private String orderId;
    private List<OrderItem> items;

    public InventoryCommand() {}

    public InventoryCommand(String sagaId, String orderId, List<OrderItem> items) {
        this.sagaId = sagaId;
        this.orderId = orderId;
        this.items = items;
    }

    // Getters & Setters
    public String getSagaId() { return sagaId; }
    public void setSagaId(String sagaId) { this.sagaId = sagaId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
