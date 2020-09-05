package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.model.OrderHistory;
import com.info.packagetrackerbackend.model.OrganizationColor;
import com.info.packagetrackerbackend.service.repository.OrderHistoryRepository;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;


@Setter
@Service
public class TransportService {
    private static final Logger logger = LogManager.getLogger(TransportService.class);

    private OrderRepository repository;
    private MessageService messageService;
    private OrderHistoryRepository historyRepository;

    public TransportService(
            OrderRepository repository,
            MessageService messageService,
            OrderHistoryRepository historyRepository
    ) {
        this.repository = repository;
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
        order.setStatus("TRANSPORT");
        order.setStatusColor(OrganizationColor.GREEN.getColor());
        repository.update(order);
        historyRepository.save(new OrderHistory(order));
        logger.info("Package is on way: " + order.toString());
        messageService.sendOrderMessage(order);
    }

}
