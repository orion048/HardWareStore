package com.project.Service;

import com.project.Model.Delivery;
import com.project.Repository.DeliveryRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery startDelivery(Long orderId) {
        Delivery delivery = new Delivery(orderId, "IN_PROGRESS");
        return deliveryRepository.save(delivery);
    }

    public Delivery completeDelivery(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow();
        delivery.setStatus("DELIVERED");
        return deliveryRepository.save(delivery);
    }
}
