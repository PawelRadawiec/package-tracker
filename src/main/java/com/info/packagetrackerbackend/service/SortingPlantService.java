package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.operations.PackageProcess;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class SortingPlantService implements Runnable, PackageProcess {

    private Order order;
    private CountDownLatch latch;

    public SortingPlantService(Order order, CountDownLatch latch) {
        this.order = order;
        this.latch = latch;
    }

    @Override
    public void run() {
        process();
    }

    @Override
    public void process() {
        order.setStatus("SORTING_PLANT");
        try {
            System.out.println("Process package in sorting plant: " +
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
