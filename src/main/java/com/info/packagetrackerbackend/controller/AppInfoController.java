package com.info.packagetrackerbackend.controller;


import com.info.packagetrackerbackend.model.Order;
import com.info.packagetrackerbackend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppInfoController {

    private OrderService service;

    public AppInfoController(OrderService service) {
        this.service = service;
    }

    @GetMapping(value = "/order/{name}")
    public ResponseEntity<String> info(@PathVariable("name") String name) {
        return new ResponseEntity<String>(service.startOrder(name), HttpStatus.OK);
    }

}
