package com.project.controller;

import com.project.dto.AuthRequest;
import com.project.dto.AuthResponse;
import com.project.dto.RegisterRequest;
import com.project.exception.InvalidLoginException;
import com.project.model.Role;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.security.CustomUserDetailsService;
import com.project.security.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// controller/AuthController.java
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // ======= API =======
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new InvalidLoginException("Неверный логин или пароль");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") RegisterRequest request, Model model) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.CUSTOMER);
            userRepository.save(user);

            // Передаём имя пользователя в шаблон
            model.addAttribute("username", user.getUsername());

            // Возвращаем имя Thymeleaf-шаблона (registration-success.html)
            return "reg-success";
        } catch (Exception e) {
            System.err.println("Ошибка при регистрации: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Ошибка при регистрации: " + e.getMessage());
            return "registration-error"; // Можно создать отдельный шаблон для ошибок
        }
    }
}

