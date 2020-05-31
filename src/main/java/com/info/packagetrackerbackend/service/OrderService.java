package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.operations.TrackerOrderOperation;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderService implements TrackerOrderOperation {

    private WarehouseService warehouseService;
    private SortingPlantService sortingPlantService;
    private TransportService transportService;
    private PackageStartService packageStartService;

    public OrderService(WarehouseService warehouseService, SortingPlantService sortingPlantService, TransportService transportService, PackageStartService packageStartService) {
        this.warehouseService = warehouseService;
        this.sortingPlantService = sortingPlantService;
        this.transportService = transportService;
        this.packageStartService = packageStartService;
    }

    @Override
    public String startOrder(String name) {
        Order order = new Order(name, "AACK");
        packageStartService.create(order);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(3);

        warehouseStep(order, latch, executorService);
        sortingPlantStep(order, latch, executorService);
        transportStep(order, latch, executorService);

        return "wait for status";
    }

    private void warehouseStep(Order order, CountDownLatch latch, ExecutorService executor) {
        warehouseService.setLatch(latch);
        warehouseService.setOrder(order);
        executor.execute(warehouseService);
    }

    private void sortingPlantStep(Order order, CountDownLatch latch, ExecutorService executor) {
        sortingPlantService.setLatch(latch);
        sortingPlantService.setOrder(order);
        executor.execute(sortingPlantService);
    }

    private void transportStep(Order order, CountDownLatch latch, ExecutorService executor) {
        transportService.setLatch(latch);
        transportService.setOrder(order);
        executor.execute(transportService);
    }

}
