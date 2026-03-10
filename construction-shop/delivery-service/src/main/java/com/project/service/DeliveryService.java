package com.project.service;

import com.hardwarestore.common.delivery.DeliveryStatus;
import com.hardwarestore.common.dto.delivery.DeliveryRequest;
import com.hardwarestore.common.dto.delivery.DeliveryResponse;
import com.project.model.DeliveryEntity;
import com.project.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {


    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public DeliveryResponse createDelivery(DeliveryRequest request) {
        DeliveryEntity entity = new DeliveryEntity();
        entity.setOrderId(request.getOrderId());
        entity.setUserId(request.getUserId());
        entity.setAddress(request.getAddress());
        entity.setStatus(DeliveryStatus.CREATED);

        DeliveryEntity saved = deliveryRepository.save(entity);
        return mapToResponse(saved);
    }

    public DeliveryResponse getById(Long id) {
        return deliveryRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }

    public List<DeliveryResponse> getByUser(Long userId) {
        return deliveryRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<DeliveryResponse> getByOrder(Long orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public DeliveryResponse updateStatus(Long id, DeliveryStatus status) {
        DeliveryEntity entity = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
        entity.setStatus(status);
        DeliveryEntity saved = deliveryRepository.save(entity);
        return mapToResponse(saved);
    }

    private DeliveryResponse mapToResponse(DeliveryEntity e) {
        DeliveryResponse dto = new DeliveryResponse();
        dto.setId(e.getId());
        dto.setOrderId(e.getOrderId());
        dto.setUserId(e.getUserId());
        dto.setAddress(e.getAddress());
        dto.setStatus(e.getStatus());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }
}
