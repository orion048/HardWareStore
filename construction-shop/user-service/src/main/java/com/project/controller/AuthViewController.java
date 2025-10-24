package com.project.controller;

import com.project.dto.RegisterRequest;
import com.project.model.Order;
import com.project.model.Product;
import com.project.model.User;
import com.project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthViewController {

    @Autowired
    UserService userService;

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
        return "login"; // имя HTML-шаблона (login.html)
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "register"; // имя HTML-шаблона (register.html)
    }

    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.getUserFromPrincipal(principal);
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        return "home";
    }



    @GetMapping("/order")
    public String showOrderPage(Model model) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Кирпич", 100, 12.5));
        products.add(new Product("Цемент", 50, 8.0));
        products.add(new Product("Песок", 200, 3.5));

        Order order = new Order("г. Москва, ул. Примерная, д.1", "Ожидается", products);
        User user = new User("Иванов Иван Иванович", "ivan@example.com", Collections.singletonList(order));

        model.addAttribute("user", user);
        return "order";
    }

}