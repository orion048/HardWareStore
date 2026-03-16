package com.project.listener;

import com.hardwarestore.common.commands.delivery.CreateDeliveryCommand;
import com.project.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryCommandListener {

    private final DeliveryService deliveryService;

    @KafkaListener(topics = "saga.commands.delivery.start", groupId = "delivery-service")
    public void onStartDelivery(CreateDeliveryCommand cmd) {
        log.info("CreateDeliveryCommand received: {}", cmd);
        deliveryService.startDelivery(cmd.getOrderId());
    }
}
