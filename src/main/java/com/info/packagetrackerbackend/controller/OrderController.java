package com.info.packagetrackerbackend.controller;


import com.info.packagetrackerbackend.model.order.Order;
import com.info.packagetrackerbackend.model.order.OrderHistory;
import com.info.packagetrackerbackend.model.order.OrderListRequest;
import com.info.packagetrackerbackend.service.OrderService;
import com.info.packagetrackerbackend.service.repository.OrderHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/order/")
public class OrderController {

    private OrderService service;
    private OrderHistoryRepository orderHistoryRepository;

    public OrderController(OrderService service, OrderHistoryRepository orderHistoryRepository) {
        this.service = service;
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @PostMapping(value = "create")
    public ResponseEntity<Order> create(@Valid @RequestBody Order order) {
        return new ResponseEntity<>(service.createOrder(order), HttpStatus.OK);
    }

    @PostMapping(value = "start")
    public ResponseEntity<Order> start(@RequestBody Order order) {
        return new ResponseEntity<>(service.startOrder(order), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/{code}")
    public ResponseEntity<Order> getOrder(
            @PathVariable("id") Long id,
            @PathVariable("code") String code
    ) {
        return new ResponseEntity<>(service.getOrder(id, code), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/history")
    public ResponseEntity<List<OrderHistory>> getHistory(@PathVariable("id") Long id) {
        return new ResponseEntity<>(orderHistoryRepository.getByOrderId(id), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<Order>> search(OrderListRequest request, Pageable pageable) {
        return new ResponseEntity<>(service.search(request, pageable), HttpStatus.OK);
    }

}
