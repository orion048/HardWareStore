package com.hardwarestore.common.dto.product;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String details;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
