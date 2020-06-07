package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.model.OrganizationColor;
import com.info.packagetrackerbackend.service.operations.PackageProcess;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ParcelLockerService implements PackageProcess {

    private static final Logger logger = LogManager.getLogger(WarehouseService.class);

    private OrderRepository orderRepository;
    private Order order;
    private CountDownLatch latch;
    private SimpMessageSendingOperations messageSending;

    public ParcelLockerService(
            OrderRepository orderRepository,
            SimpMessageSendingOperations messageSending
    ) {
        this.orderRepository = orderRepository;
        this.messageSending = messageSending;
    }

    @Override
    public void run() {
        process();
    }

    @Override
    public void process() {
        order.setStatus("PARCEL_LOCKER");
        order.setStatusColor(OrganizationColor.ORANGE.getColor());
        orderRepository.update(order);
        try {
            logger.info("Process package in parcel locker: " + order.toString());
            Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 5_000));
            sendMessage(order);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            latch.countDown();
        }
    }

    @Override
    public void sendMessage(Order order) {
        messageSending.convertAndSend("/topic/package", order);
    }

}
