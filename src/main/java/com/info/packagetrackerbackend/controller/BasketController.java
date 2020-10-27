package com.info.packagetrackerbackend.controller;

import com.info.packagetrackerbackend.model.basket.AddToBasket;
import com.info.packagetrackerbackend.model.basket.Basket;
import com.info.packagetrackerbackend.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/basket/")
public class BasketController {

    private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping(value = "/owner")
    public ResponseEntity<Basket> getByOwner() {
        return new ResponseEntity<>(basketService.getByOwner(), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Basket> addProduct(@RequestBody AddToBasket request) {
        return new ResponseEntity<>(basketService.addProduct(request), HttpStatus.OK);
    }

}
