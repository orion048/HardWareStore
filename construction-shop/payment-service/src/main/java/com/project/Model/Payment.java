package com.project.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", schema = "payment_service")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private double amount;
    private String status; // например: PENDING, SUCCESS, FAILED
    private LocalDateTime createdAt;

    public Payment() {
        this.createdAt = LocalDateTime.now();
    }

    public Payment(Long orderId, double amount, String status) {
        this.orderId = orderId;
        this.amount = amount;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
