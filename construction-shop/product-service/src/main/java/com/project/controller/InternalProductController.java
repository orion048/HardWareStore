package com.project.controller;

import com.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/internal/products")
@RequiredArgsConstructor
public class InternalProductController {

    private final ProductService productService;

    @GetMapping("/{id}/price")
    public ResponseEntity<BigDecimal> getPrice(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getPriceById(id));
    }
}
