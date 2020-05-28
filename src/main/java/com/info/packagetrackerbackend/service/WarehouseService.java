package com.info.packagetrackerbackend.service;


import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.operations.PackageProcess;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class WarehouseService implements Runnable, PackageProcess {

    private Order order;
    private CountDownLatch latch;

    public WarehouseService(Order order, CountDownLatch latch) {
        this.order = order;
        this.latch = latch;
    }

    @Override
    public void run() {
        process();
    }

    @Override
    public void process() {
        order.setStatus("WAREHOUSE");
        try {
            System.out.println("Process package in warehouse: " +
                    order.getName() + " | code: " +
                    order.getCode() + " | status: " +
                    order.getStatus());

            Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 5_000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }


}
