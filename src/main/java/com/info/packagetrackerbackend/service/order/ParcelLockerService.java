package com.info.packagetrackerbackend.service.order;

import com.info.packagetrackerbackend.model.order.Order;
import com.info.packagetrackerbackend.model.order.OrderHistory;
import com.info.packagetrackerbackend.model.OrganizationColor;
import com.info.packagetrackerbackend.service.MessageService;
import com.info.packagetrackerbackend.service.repository.OrderHistoryRepository;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
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

    public Runnable process(Order order, CountDownLatch latch) {
        return () -> {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 10_000));
                order.setOrderEndDate(LocalDate.now());
                create(order);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private void create(Order order) {
        order.setStatus("PARCEL_LOCKER");
        order.setStatusColor(OrganizationColor.ORANGE.getColor());
        orderRepository.save(order);
        historyRepository.save(new OrderHistory(order));
        logger.info("Process package in parcel locker: " + order.toString());
        messageService.sendOrderMessage(order);
    }

}
