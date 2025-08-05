package com.project.controller;

import com.project.dto.RegisterRequest;
import com.project.dto.UpdateUserRequest;
import com.project.dto.UserResponse;
import com.project.exception.UserNotFoundException;
import com.project.model.User;
import com.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User createdUser = service.registerUser(request);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = service.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        return ResponseEntity.ok(new UserResponse(user));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        User updatedUser = service.updateUser(id, request);
        return ResponseEntity.ok(new UserResponse(updatedUser));
    }

}