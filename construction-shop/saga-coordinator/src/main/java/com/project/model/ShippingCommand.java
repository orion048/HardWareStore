package com.project.model;

public class ShippingCommand {
    private String sagaId;
    private String orderId;
    private String address;

    public ShippingCommand() {}

    public ShippingCommand(String sagaId, String orderId, String address) {
        this.sagaId = sagaId;
        this.orderId = orderId;
        this.address = address;
    }

    // Getters & Setters
    public String getSagaId() { return sagaId; }
    public void setSagaId(String sagaId) { this.sagaId = sagaId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
