package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderService implements TrackerOrderOperation {

    @Override
    public String startOrder(String name) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(5);
        List<String> statusList = Arrays.asList(
                "order", "in way to warehouse",
                "warehouse", "send to you :)",
                "you can pick up your package"
        );
        for (int i = 0; i < 5; i++) {
            int index = i + 1;
            Order newOrder = new Order(name, "AACK" + index, latch);
            newOrder.setId((long) index);
            newOrder.setStatus(statusList.get(i));
            executorService.execute(newOrder);
        }
        return "wait for status";
    }

}
