package com.project.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries", schema = "delivery_service")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String status; // например: PENDING, IN_PROGRESS, DELIVERED
    private LocalDateTime createdAt;

    public Delivery() {
        this.createdAt = LocalDateTime.now();
    }

    public Delivery(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // getters/setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
