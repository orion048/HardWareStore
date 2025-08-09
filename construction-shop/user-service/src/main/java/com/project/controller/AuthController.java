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
@RestController
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
    @ResponseBody
    public ResponseEntity<String> register(@ModelAttribute("user") RegisterRequest request) {
        System.out.println(">>> Inside createUser method");
        try {
            System.out.println("Сохраняем пользователя: " + request);
            logger.debug("Received request to create user: {}", request);
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.CUSTOMER);
            System.out.println("Username: " + request.getUsername());
            System.out.println("Email: " + request.getEmail());
            System.out.println("Password: " + request.getPassword());

            User savedUser = userRepository.save(user);
            System.out.println("Saved user ID: " + savedUser.getId());

            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            System.err.println("Ошибка при регистрации: " + e.getMessage());
            e.printStackTrace(); // 👈 покажет стек вызовов
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при регистрации: " + e.getMessage());
        }
    }
}

