package com.project.cartservice.Service;

import com.project.cartservice.Model.CartItem;
import com.project.cartservice.Repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartItem addItem(CartItem item) {
        return cartRepository.save(item);
    }

    public List<CartItem> getUserCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void removeItem(Long id) {
        cartRepository.deleteById(id);
    }
}
