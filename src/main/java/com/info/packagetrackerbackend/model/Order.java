package com.info.packagetrackerbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@NoArgsConstructor
public class Order implements Runnable {

    private Long id;
    private String name;
    private String code;
    private String status;
    private CountDownLatch latch;

    public Order(String name, String code, CountDownLatch latch) {
        this.name = name;
        this.code = code;
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Order with name: " + this.name + " | status: " + this.status);
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 5_000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }

}
