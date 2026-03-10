package com.project.cartservice.model;


import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Long userId;
    private List<CartItem> items = new ArrayList<>();

    public Cart() {
    }

    public Cart(Long userId, List<CartItem> items) {
        this.userId = userId;
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}

