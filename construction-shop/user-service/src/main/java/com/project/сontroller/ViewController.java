package com.project.сontroller;


import com.project.dto.RegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
