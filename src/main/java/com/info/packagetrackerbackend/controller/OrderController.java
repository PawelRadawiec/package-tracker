package com.info.packagetrackerbackend.controller;


import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.OrderService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@Scope("prototype")
@RequestMapping(value = "/order/")
public class OrderController {

    private OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
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

}
