package com.info.packagetrackerbackend.service.operations;

import com.info.packagetrackerbackend.model.order.Order;
import com.info.packagetrackerbackend.model.order.OrderListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrackerOrderOperation {

    Order startOrder(Order order);

    Order getOrder(Long id, String code);

    Page<Order> search(OrderListRequest request, Pageable pageable);

}
