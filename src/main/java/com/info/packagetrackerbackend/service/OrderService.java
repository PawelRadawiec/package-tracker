package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.model.OrderListRequest;
import com.info.packagetrackerbackend.service.operations.TrackerOrderOperation;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import com.info.packagetrackerbackend.service.repository.specification.OrderListSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Transactional
public class OrderService implements TrackerOrderOperation {
    private WarehouseService warehouseService;
    private SortingPlantService sortingPlantService;
    private TransportService transportService;
    private ParcelLockerService lockerService;
    private PackageStartService packageStartService;
    private OrderRepository orderRepository;
    private OrderListSpecification listSpecification;

    public OrderService(
            WarehouseService warehouseService, SortingPlantService sortingPlantService,
            TransportService transportService, ParcelLockerService lockerService,
            PackageStartService packageStartService, OrderRepository orderRepository,
            OrderListSpecification listSpecification) {
        this.warehouseService = warehouseService;
        this.sortingPlantService = sortingPlantService;
        this.transportService = transportService;
        this.lockerService = lockerService;
        this.packageStartService = packageStartService;
        this.orderRepository = orderRepository;
        this.listSpecification = listSpecification;
    }

    public Order createOrder(Order order) {
        expandOrderCode(order);
        packageStartService.create(order);
        return order;
    }

    @Override
    public Order startOrder(Order order) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CountDownLatch countDownLatch = new CountDownLatch(4);
        Arrays.asList(
                warehouseService.process(order, countDownLatch),
                sortingPlantService.process(order, countDownLatch),
                transportService.process(order, countDownLatch),
                lockerService.process(order, countDownLatch)
        ).forEach(executorService::execute);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return order;
    }

    @Override
    public Order getOrder(Long id, String code) {
        return packageStartService.getOrder(id, code);
    }

    @Override
    public Page<Order> search(OrderListRequest request, Pageable pageable) {
        return orderRepository.findAll(listSpecification.getFilter(request), pageable);
    }

    private void expandOrderCode(Order order) {
        order.setCode(RandomStringUtils.randomAlphanumeric(20));
    }

}
