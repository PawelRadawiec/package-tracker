package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.model.OrderHistory;
import com.info.packagetrackerbackend.model.OrganizationColor;
import com.info.packagetrackerbackend.service.repository.OrderHistoryRepository;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ParcelLockerService {
    private static final Logger logger = LogManager.getLogger(ParcelLockerService.class);

    private OrderRepository orderRepository;
    private MessageService messageService;
    private OrderHistoryRepository historyRepository;

    public ParcelLockerService(
            OrderRepository orderRepository,
            MessageService messageService,
            OrderHistoryRepository historyRepository
    ) {
        this.orderRepository = orderRepository;
        this.messageService = messageService;
        this.historyRepository = historyRepository;
    }

    public Runnable process(Order order) {
        return () -> {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 10_000));
                create(order);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private void create(Order order) {
        order.setStatus("PARCEL_LOCKER");
        order.setStatusColor(OrganizationColor.ORANGE.getColor());
        orderRepository.update(order);
        historyRepository.save(new OrderHistory(order));
        logger.info("Process package in parcel locker: " + order.toString());
        messageService.sendOrderMessage(order);
    }

}
