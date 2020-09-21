package com.info.packagetrackerbackend.service.operations;

import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.model.OrderListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface TrackerOrderOperation {

    Order startOrder(Order order);

    Order getOrder(Long id, String code);

    Page<Order> search(OrderListRequest request, Pageable pageable);

}
