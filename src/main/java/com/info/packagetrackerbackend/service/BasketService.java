package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.basket.AddToBasket;
import com.info.packagetrackerbackend.model.basket.Basket;
import com.info.packagetrackerbackend.service.repository.BasketRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BasketService {

    private ProductService productService;
    private BasketRepository basketRepository;
    private SystemUserHelper userHelper;

    public BasketService(ProductService productService, BasketRepository basketRepository, SystemUserHelper userHelper) {
        this.productService = productService;
        this.basketRepository = basketRepository;
        this.userHelper = userHelper;
    }

    public Basket create(Basket basket) {
        basketRepository.save(basket);
        return basket;
    }

    public Basket addProduct(AddToBasket request) {
        Product product = request.getProduct();
        Basket basket = request.getBasket();
        product.setBasket(basket);
        productService.create(product);
        return basket;
    }

    public Basket getByOwner() {
        return basketRepository.getByOwnerId(userHelper.getCurrentUser().getId());
    }

    public Long count() {
        return basketRepository.countProductsInBasket(userHelper.getCurrentUser().getId());
    }

    public Basket deleteProduct(Long basketId, Product product) {
        Basket basket = basketRepository.findById(basketId).orElseGet(Basket::new);
        basket.getProducts().forEach(p -> deleteBasketFromProduct(p, product.getId()));
        basketRepository.save(basket);
        return basket;
    }

    private void deleteBasketFromProduct(Product product, Long deleteProductId) {
        if (product.getId().equals(deleteProductId)) {
            product.setBasket(null);
        }
    }

}
