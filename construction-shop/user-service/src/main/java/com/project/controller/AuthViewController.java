package com.project.controller;

import com.project.dto.RegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthViewController {

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
}
