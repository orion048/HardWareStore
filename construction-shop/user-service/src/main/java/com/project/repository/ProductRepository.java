package com.project.repository;

import com.project.model.CartItem;
import com.project.model.Product;
import com.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
