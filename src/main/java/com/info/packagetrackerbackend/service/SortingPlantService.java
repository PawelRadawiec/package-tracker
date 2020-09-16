package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.model.OrderHistory;
import com.info.packagetrackerbackend.model.OrganizationColor;
import com.info.packagetrackerbackend.service.repository.OrderHistoryRepository;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class SortingPlantService {
    private static final Logger logger = LogManager.getLogger(SortingPlantService.class);

    private OrderRepository repository;
    private MessageService messageService;
    private OrderHistoryRepository historyRepository;

    public SortingPlantService(
            OrderRepository repository,
            MessageService messageService,
            OrderHistoryRepository historyRepository
    ) {
        this.repository = repository;
        this.messageService = messageService;
        this.historyRepository = historyRepository;
    }

    public Runnable process(Order order, CountDownLatch latch) {
        return () -> {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 10_000));
                create(order);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private void create(Order order) {
        order.setStatus("SORTING_PLANT");
        order.setStatusColor(OrganizationColor.YELLOW.getColor());
        repository.save(order);
        historyRepository.save(new OrderHistory(order));
        logger.info("Process package in sorting plant: " + order.toString());
        messageService.sendOrderMessage(order);
    }

}
