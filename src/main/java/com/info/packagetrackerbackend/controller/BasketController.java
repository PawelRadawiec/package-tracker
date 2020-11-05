package com.info.packagetrackerbackend.controller;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.basket.AddToBasket;
import com.info.packagetrackerbackend.model.basket.Basket;
import com.info.packagetrackerbackend.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
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

    @GetMapping(value = "/count")
    public ResponseEntity<Long> counter() {
        return new ResponseEntity<>(basketService.count(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{basketId}/product")
    public ResponseEntity<Basket> deleteProduct(@PathVariable("basketId") Long basketId, @RequestBody Product product) {
        return new ResponseEntity<>(basketService.deleteProduct(basketId, product), HttpStatus.OK);
    }

}
