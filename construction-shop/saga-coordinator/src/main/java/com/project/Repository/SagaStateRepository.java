package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SagaStateRepository extends JpaRepository<SagaState, String> {
    Optional<SagaState> findByOrderId(String orderId);
}