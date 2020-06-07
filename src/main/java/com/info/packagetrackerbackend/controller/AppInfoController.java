package com.info.packagetrackerbackend.controller;


import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.OrderService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Scope("prototype")
@RequestMapping(value = "/order/")
public class AppInfoController {

    private OrderService service;

    public AppInfoController(OrderService service) {
        this.service = service;
    }

    @PostMapping(value = "start")
    public ResponseEntity<Order> start(@RequestBody Order order) {
        return new ResponseEntity<Order>(service.startOrder(order), HttpStatus.OK);
    }

}
