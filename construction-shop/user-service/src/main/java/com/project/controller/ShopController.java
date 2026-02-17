package com.project.controller;

import com.project.model.*;
import com.project.repository.CartItemRepository;
import com.project.repository.OrderRepository;
import com.project.repository.ProductRepository;
import com.project.service.CartService;
import com.project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    private User getCurrentUser() {
        List<User> users = userService.getAll();

        if (users.isEmpty()) {
            User guest = new User("Гость", "guest@example.com", null, Role.GUEST);
            guest = userService.save(guest); // сохраняем в базу
            return guest;
        }

        return users.get(users.size() - 1);
    }



    @GetMapping("/products")
    public String showProducts(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User("Гость", "guest@example.com", null);
        }
        model.addAttribute("user", user);
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }


    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam int quantity,
                            HttpSession session) {
        // Получаем пользователя из сессии
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }

        // Получаем товар
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));

        // Проверяем, есть ли уже такой товар в корзине
        Optional<CartItem> existingItemOpt = cartItemRepository.findByUserAndProduct(user, product);

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }

        // Редирект на корзину
        return "redirect:/shop/cart";
    }


    @GetMapping("/cart")
    public String cartPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);

        return "cart";
    }



    @PostMapping("/checkout")
    public String checkout(@RequestParam String deliveryAddress, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/auth/login";

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) return "redirect:/shop/cart";

        Optional<Order> existingOrderOpt = orderRepository.findByUser(user);
        Order order;

        if (existingOrderOpt.isPresent()) {
            order = existingOrderOpt.get();
            order.getItems().clear(); // очищаем старые позиции
        } else {
            order = new Order();
            order.setUser(user);
        }

        order.setDeliveryAddress(deliveryAddress);
        order.setStatus("Ожидает подтверждения");

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem(item.getProduct(), item.getQuantity(), item.getProduct().getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setItems(orderItems);
        orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);

        return "redirect:/auth/profile";
    }




    @GetMapping("/cart-count")
    @ResponseBody
    public String getCartCount(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "0";

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        int totalQuantity = cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        return String.valueOf(totalQuantity);
    }

}
