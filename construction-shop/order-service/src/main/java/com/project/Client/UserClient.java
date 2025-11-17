package com.project.Client;


import com.project.DTO.ProductDto;
import com.project.DTO.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Клиент для user-service
@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
}

// Клиент для product-service
@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);
}