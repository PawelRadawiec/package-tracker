package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.auth.SystemUser;
import com.info.packagetrackerbackend.model.basket.AddToBasket;
import com.info.packagetrackerbackend.model.basket.Basket;
import com.info.packagetrackerbackend.service.repository.BasketRepository;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BasketService {

    private ProductService productService;
    private BasketRepository basketRepository;
    private SystemUserRepository userRepository;

    public BasketService(ProductService productService, BasketRepository basketRepository, SystemUserRepository userRepository) {
        this.productService = productService;
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUser user = userRepository.findByUsername(authentication.getName()).orElseGet(SystemUser::new);
        return basketRepository.getByOwnerId(user.getId());
    }

}
