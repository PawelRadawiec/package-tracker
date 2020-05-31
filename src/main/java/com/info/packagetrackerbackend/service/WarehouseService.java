package com.info.packagetrackerbackend.service;


import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.operations.PackageProcess;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WarehouseService implements Runnable, PackageProcess {

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
            System.out.println("Process package in warehouse: " + order.getName() + " | code: " + order.getCode() + " | status: " + order.getStatus());

            Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 5_000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }


}
