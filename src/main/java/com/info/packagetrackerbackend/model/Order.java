package com.info.packagetrackerbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.CountDownLatch;

@Getter
@Setter
@NoArgsConstructor
public class Order {

    private Long id;
    private String name;
    private String code;
    private String status;
    private CountDownLatch latch;

    public Order(String name, String code) {
        this.name = name;
        this.code = code;
    }

}
