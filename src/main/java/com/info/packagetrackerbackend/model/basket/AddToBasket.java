package com.info.packagetrackerbackend.model.basket;

import com.info.packagetrackerbackend.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToBasket {

    private Basket basket;
    private Product product;

}
