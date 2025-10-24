package com.project.service;

import com.project.model.CartItem;
import com.project.model.Product;
import com.project.model.User;
import com.project.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public void addToCart(User user, Product product, int quantity) {
        CartItem item = new CartItem();
        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    public void clearCart(User user) {
        cartItemRepository.deleteAll(cartItemRepository.findByUser(user));
    }
}
