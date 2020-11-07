package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.basket.AddToBasket;
import com.info.packagetrackerbackend.model.basket.Basket;
import com.info.packagetrackerbackend.service.repository.BasketRepository;
import com.info.packagetrackerbackend.service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
public class BasketService {

    private BasketRepository basketRepository;
    private ProductRepository productRepository;
    private SystemUserHelper userHelper;

    public BasketService(BasketRepository basketRepository, ProductRepository productRepository, SystemUserHelper userHelper) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.userHelper = userHelper;
    }

    public Basket create(Basket basket) {
        basketRepository.save(basket);
        return basket;
    }

    public Basket addProductToBasket(AddToBasket addToBasket) {
        Basket basket = basketRepository.findById(addToBasket.getBasket().getId()).orElseGet(Basket::new);
        Product fullProduct = productRepository.findById(addToBasket.getProduct().getId()).orElseGet(Product::new);
        fullProduct.setInBasket(true);
        fullProduct.setBasket(basket);
        basket.getProducts().add(fullProduct);
        basketRepository.save(basket);
        return basket;
    }

    private Basket getById(Long id) {
        return basketRepository.findById(id).orElseGet(Basket::new);
    }

    public Basket getByOwner() {
        return basketRepository.getByOwnerId(userHelper.getCurrentUser().getId());
    }

    public Long count() {
        return basketRepository.countProductsInBasket(userHelper.getCurrentUser().getId());
    }

    public Basket deleteProductFromBasket(Long basketId, Product product) {
        Product dbProduct = productRepository.findById(product.getId()).orElseGet(Product::new);
        dbProduct.setBasket(null);
        dbProduct.setInBasket(false);
        productRepository.save(dbProduct);
        return getById(basketId);
    }

}
