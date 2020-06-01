package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.operations.PackageProcess;
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
    private ParcelLockerService lockerService;
    private PackageStartService packageStartService;

    public OrderService(
            WarehouseService warehouseService, SortingPlantService sortingPlantService,
            TransportService transportService, ParcelLockerService lockerService,
            PackageStartService packageStartService
    ) {
        this.warehouseService = warehouseService;
        this.sortingPlantService = sortingPlantService;
        this.transportService = transportService;
        this.lockerService = lockerService;
        this.packageStartService = packageStartService;
    }

    @Override
    public String startOrder(String name) {
        Order order = new Order(name, "AACK");
        packageStartService.create(order);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(3);

        executeService(warehouseService, order, latch, executorService);
        executeService(sortingPlantService, order, latch, executorService);
        executeService(transportService, order, latch, executorService);
        executeService(lockerService, order, latch, executorService);
        executorService.shutdown();

        return "wait for status";
    }

    private void executeService(
            PackageProcess packageProcess, Order order,
            CountDownLatch latch, ExecutorService executor
    ) {
        packageProcess.setLatch(latch);
        packageProcess.setOrder(order);
        executor.execute(packageProcess);
    }

}
