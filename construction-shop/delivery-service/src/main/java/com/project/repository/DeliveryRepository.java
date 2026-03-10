package com.project.repository;


import com.project.model.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    List<DeliveryEntity> findByUserId(Long userId);

    List<DeliveryEntity> findByOrderId(Long orderId);
}

