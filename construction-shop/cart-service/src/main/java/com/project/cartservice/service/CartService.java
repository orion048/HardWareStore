package com.project.cartservice.service;

import com.hardwarestore.common.dto.cart.AddToCartRequest;
import com.hardwarestore.common.dto.cart.CartItemResponse;
import com.hardwarestore.common.dto.cart.CartResponse;
import com.project.cartservice.model.Cart;
import com.project.cartservice.model.CartItem;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CART_KEY = "cart:";

    public CartService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public CartResponse addItem(Long userId, AddToCartRequest request) {
        Cart cart = getCartInternal(userId);

        cart.getItems().stream()
                .filter(i -> i.getProductId().equals(request.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        i -> i.setQuantity(i.getQuantity() + request.getQuantity()),
                        () -> cart.getItems().add(new CartItem(request.getProductId(), request.getQuantity()))
                );

        saveCart(cart);
        return toResponse(cart);
    }

    public CartResponse getCart(Long userId) {
        return toResponse(getCartInternal(userId));
    }

    public CartResponse removeItem(Long userId, Long productId) {
        Cart cart = getCartInternal(userId);
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        saveCart(cart);
        return toResponse(cart);
    }

    public void clearCart(Long userId) {
        redisTemplate.delete(CART_KEY + userId);
    }

    private Cart getCartInternal(Long userId) {
        Object data = redisTemplate.opsForValue().get(CART_KEY + userId);
        if (data == null) {
            return new Cart(userId, new ArrayList<>());
        }
        return (Cart) data;
    }

    private void saveCart(Cart cart) {
        redisTemplate.opsForValue().set(CART_KEY + cart.getUserId(), cart);
    }

    private CartResponse toResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(i -> new CartItemResponse(i.getProductId(), i.getQuantity()))
                .toList();

        return new CartResponse(cart.getUserId(), items);
    }
}

