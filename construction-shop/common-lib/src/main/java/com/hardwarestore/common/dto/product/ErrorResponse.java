package com.hardwarestore.common.dto.product;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String details;
}
