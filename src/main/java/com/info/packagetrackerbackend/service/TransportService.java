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

    public void process(Order order) {
        order.setStatus("TRANSPORT");
        order.setStatusColor(OrganizationColor.GREEN.getColor());
        repository.update(order);
        historyRepository.save(new OrderHistory(order));
        try {
            logger.info("Package is on way: " + order.toString());
            Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 20_000));
            messageService.sendOrderMessage(order);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

}
