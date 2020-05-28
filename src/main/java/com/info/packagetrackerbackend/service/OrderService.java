package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.operations.TrackerOrderOperation;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderService implements TrackerOrderOperation {

    @Override
    public String startOrder(String name) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(5);
        Order order = new Order(name, "AACK");
        executorService.execute(new WarehouseService(order, latch));
        executorService.execute(new SortingPlantService(order, latch));
        return "wait for status";
    }

}
