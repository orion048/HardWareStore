package com.project.listener;

import com.hardwarestore.common.commands.order.ApproveOrderCommand;
import com.hardwarestore.common.commands.order.CancelOrderCommand;
import com.hardwarestore.common.commands.order.CompleteOrderCommand;
import com.project.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderCommandListener {

    @Autowired
    OrderService orderService;

    @KafkaListener(topics = "saga.commands.order.cancel", groupId = "order-service")
    public void onCancel(CancelOrderCommand cmd) {
        log.warn("CancelOrderCommand received: {}", cmd);
        orderService.cancelOrder(cmd.getOrderId(), cmd.getReason());
    }

    @KafkaListener(topics = "saga.commands.order.approve", groupId = "order-service")
    public void onApprove(ApproveOrderCommand cmd) {
        log.info("ApproveOrderCommand received: {}", cmd);
        orderService.approveOrder(cmd.getOrderId());
    }

    @KafkaListener(topics = "saga.commands.order.complete", groupId = "order-service")
    public void onComplete(CompleteOrderCommand cmd) {
        log.info("CompleteOrderCommand received: {}", cmd);
        orderService.completeOrder(cmd.getOrderId());
    }
}
