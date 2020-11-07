package com.info.packagetrackerbackend.controller;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.basket.AddToBasket;
import com.info.packagetrackerbackend.model.basket.Basket;
import com.info.packagetrackerbackend.service.BasketService;
import com.info.packagetrackerbackend.validators.AddToBasketValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/basket/")
public class BasketController {

    private BasketService basketService;
    private AddToBasketValidator addToBasketValidator;

    public BasketController(BasketService basketService, AddToBasketValidator addToBasketValidator) {
        this.basketService = basketService;
        this.addToBasketValidator = addToBasketValidator;
    }

    @InitBinder
    private void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(addToBasketValidator);
    }

    @GetMapping(value = "/owner")
    public ResponseEntity<Basket> getByOwner() {
        return new ResponseEntity<>(basketService.getByOwner(), HttpStatus.OK);
    }

    @PostMapping(value = "/add/product")
    public ResponseEntity<Basket> addProduct(@Valid @RequestBody AddToBasket addToBasket) {
        return new ResponseEntity<>(basketService.addProduct(addToBasket), HttpStatus.OK);
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
