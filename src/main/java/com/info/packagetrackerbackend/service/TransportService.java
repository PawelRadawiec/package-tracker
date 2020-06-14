package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.model.OrderHistory;
import com.info.packagetrackerbackend.model.OrganizationColor;
import com.info.packagetrackerbackend.service.operations.PackageProcess;
import com.info.packagetrackerbackend.service.repository.OrderHistoryRepository;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@Setter
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TransportService implements PackageProcess {

    private static final Logger logger = LogManager.getLogger(TransportService.class);

    private Order order;
    private CountDownLatch latch;
    private OrderRepository repository;
    private SimpMessageSendingOperations messageSending;
    private OrderHistoryRepository historyRepository;

    public TransportService(
            OrderRepository repository,
            SimpMessageSendingOperations messageSending,
            OrderHistoryRepository historyRepository
    ) {
        this.repository = repository;
        this.messageSending = messageSending;
        this.historyRepository = historyRepository;
    }

    @Override
    public void run() {
        process();
    }

    @Override
    public void process() {
        order.setStatus("TRANSPORT");
        order.setStatusColor(OrganizationColor.GREEN.getColor());
        repository.update(order);
        historyRepository.save(new OrderHistory(order));
        try {
            logger.info("Package is on way: " + order.toString());
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
