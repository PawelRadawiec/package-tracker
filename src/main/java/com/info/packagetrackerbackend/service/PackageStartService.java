package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class PackageStartService {

    private OrderRepository repository;

    public PackageStartService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(Order order) {
        repository.save(order);
        return order;
    }

    public Order getOrder(Long id, String code) {
        return repository.getOrderByIdAndCode(id, code);
    }

}
