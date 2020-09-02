package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.operations.TrackerOrderOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
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

    public Order createOrder(Order order) {
        expandOrderCode(order);
        packageStartService.create(order);
        return order;
    }

    @Override
    public Order startOrder(Order order) {
        warehouseService.process(order);
        sortingPlantService.process(order);
        transportService.process(order);
        lockerService.process(order);
        return order;
    }

    @Override
    public Order getOrder(Long id, String code) {
        return packageStartService.getOrder(id, code);
    }

    private void expandOrderCode(Order order) {
        order.setCode(RandomStringUtils.randomAlphanumeric(20));
    }

}
