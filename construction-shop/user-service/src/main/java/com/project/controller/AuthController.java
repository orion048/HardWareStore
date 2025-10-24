package com.project.controller;

import com.project.dto.RegisterRequest;
import com.project.model.Order;
import com.project.model.Product;
import com.project.model.Role;
import com.project.model.User;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ======= Регистрация =======
    @PostMapping("/register")
    public String register(@ModelAttribute("user") RegisterRequest request, Model model) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.CUSTOMER);

            Order order = new Order();
            order.setDeliveryAddress("Москва, ул. Примерная, д.1");
            order.setStatus("Новый");
            order.setUser(user);

            Product product1 = new Product();
            product1.setProductName("Цемент");
            product1.setQuantity(10);
            product1.setPrice(500);
            product1.setOrder(order);

            Product product2 = new Product();
            product2.setProductName("Кирпич");
            product2.setQuantity(100);
            product2.setPrice(15);
            product2.setOrder(order);

            order.setProducts(List.of(product1, product2));
            user.setOrders(List.of(order));

            userRepository.save(user);

            // 🔁 После регистрации — перенаправляем на страницу входа
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при регистрации: " + e.getMessage());
            return "registration-error";
        }
    }

    // ======= Профиль пользователя =======
    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Пользователь не найден");
            return "error";
        }

        model.addAttribute("user", userOptional.get());
        return "profile";
    }

    // ======= Страница входа =======
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверный логин или пароль");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Вы вышли из системы");
        }
        return "login";
    }
}
