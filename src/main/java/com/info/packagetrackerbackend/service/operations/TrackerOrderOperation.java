package com.info.packagetrackerbackend.service.operations;

import com.info.packagetrackerbackend.model.Order;

import java.util.List;

public interface TrackerOrderOperation {

    Order startOrder(Order order);

    Order getOrder(Long id, String code);

    List<Order> search();

}
