package com.project.dto;

import lombok.Data;

@Data
public class OrderPaymentUpdateRequest {
    private Long paymentId;
    private boolean success;
}

