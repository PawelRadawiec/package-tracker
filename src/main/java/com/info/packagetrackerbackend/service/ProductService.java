package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.auth.SystemUser;
import com.info.packagetrackerbackend.service.repository.ProductRepository;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private SystemUserRepository userRepository;

    public ProductService(ProductRepository productRepository, SystemUserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Product create(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUser user = userRepository.findByUsername(authentication.getName()).orElseGet(SystemUser::new);
        product.setOwner(user);
        productRepository.save(product);
        return product;
    }

    public Page<Product> getProducts(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUser user = userRepository.findByUsername(authentication.getName()).orElseGet(SystemUser::new);
        return productRepository.findByOwnerId(user.getId(), pageable);
    }

}
