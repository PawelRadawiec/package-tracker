package com.info.packagetrackerbackend.service.operations;

import com.info.packagetrackerbackend.model.order.Order;

import java.util.concurrent.CountDownLatch;

public interface PackageProcess extends Runnable {

    void process();

    void setLatch(CountDownLatch latch);

    void setOrder(Order order);

    void sendMessage(Order order);

}
