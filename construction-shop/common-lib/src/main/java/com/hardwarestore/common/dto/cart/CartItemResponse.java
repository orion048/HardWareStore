package com.hardwarestore.common.dto.cart;


public class CartItemResponse {
    private Long productId;
    private Integer quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

