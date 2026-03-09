package com.project.сontroller;

import com.project.dto.RegisterRequest;
import com.project.dto.UserResponse;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ---------------------------
    // PUBLIC ENDPOINTS
    // ---------------------------

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> getByEmail(@RequestParam String email) {
        UserResponse user = userService.getByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }



    // ---------------------------
    // INTERNAL ENDPOINTS (для order-service, gateway, saga)
    // ---------------------------

    @GetMapping("/internal/{email}")
    public ResponseEntity<UserResponse> getInternal(@PathVariable String email) {
        UserResponse user = userService.getByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

}
