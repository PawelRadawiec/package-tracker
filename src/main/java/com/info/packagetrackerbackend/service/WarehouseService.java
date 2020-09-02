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
public class WarehouseService {
    private static final Logger logger = LogManager.getLogger(WarehouseService.class);

    private OrderRepository repository;
    private MessageService messageService;
    private OrderHistoryRepository historyRepository;

    public WarehouseService(
            OrderRepository repository,
            MessageService messageService,
            OrderHistoryRepository historyRepository
    ) {
        this.repository = repository;
        this.messageService = messageService;
        this.historyRepository = historyRepository;
    }

    public void process(Order order) {
        order.setStatus("WAREHOUSE");
        order.setStatusColor(OrganizationColor.BLUE.getColor());
        repository.update(order);
        historyRepository.save(new OrderHistory(order));
        try {
            logger.info("Process package in warehouse: " + order.toString());
            Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 20_000));
            messageService.sendOrderMessage(order);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

}
