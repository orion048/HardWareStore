package com.project.controller;

import com.project.model.Product;
import com.project.model.User;
import com.project.repository.ProductRepository;
import com.project.service.CartService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("/products")
    public String showProducts(Model model, Principal principal) {
        User user = userService.getUserFromPrincipal(principal);
        model.addAttribute("user", user); // 👈 передаём пользователя в шаблон
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, Principal principal) {
        User user = userService.getUserFromPrincipal(principal); // реализуй метод
        Product product = productRepository.findById(productId).orElseThrow();
        cartService.addToCart(user, product, quantity);
        return "redirect:/shop/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login"; // или покажи страницу с сообщением
        }

        User user = userService.getUserFromPrincipal(principal);
        model.addAttribute("cartItems", cartService.getCartItems(user));
        return "cart";
    }


    @PostMapping("/checkout")
    public String checkout(Principal principal) {
        User user = userService.getUserFromPrincipal(principal);
        // Создай заказ из корзины
        cartService.clearCart(user);
        return "redirect:/shop/products";
    }

    @GetMapping("/cart-count")
    @ResponseBody
    public String getCartCount(Principal principal) {
        if (principal == null) {
            return "0"; // или верни пустую корзину
        }
        User user = userService.getUserFromPrincipal(principal);
        int count = cartService.getCartItems(user).size();
        return String.valueOf(count);
    }


}
