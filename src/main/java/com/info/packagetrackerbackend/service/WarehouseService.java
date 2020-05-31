package com.info.packagetrackerbackend.service;


import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.operations.PackageProcess;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@Setter
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WarehouseService implements PackageProcess {

    private static final Logger logger = LogManager.getLogger(WarehouseService.class);

    private Order order;
    private CountDownLatch latch;
    private OrderRepository repository;

    public WarehouseService(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        process();
    }

    @Override
    public void process() {
        order.setStatus("WAREHOUSE");
        repository.update(order);
        try {
            logger.info("Process package in warehouse: " + order.toString());
            Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 5_000));
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            latch.countDown();
        }
    }


}
