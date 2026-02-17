package com.project.model;

public class InventoryResult {
    private String sagaId;
    private String orderId;
    private boolean success;
    private String reason;

    public InventoryResult() {}

    public InventoryResult(String sagaId, String orderId, boolean success, String reason) {
        this.sagaId = sagaId;
        this.orderId = orderId;
        this.success = success;
        this.reason = reason;
    }

    // Getters & Setters
    public String getSagaId() { return sagaId; }
    public void setSagaId(String sagaId) { this.sagaId = sagaId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
