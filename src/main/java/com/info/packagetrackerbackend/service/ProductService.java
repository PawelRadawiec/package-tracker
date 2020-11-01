package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.service.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private SystemUserHelper userHelper;

    public ProductService(ProductRepository productRepository, SystemUserHelper userHelper) {
        this.productRepository = productRepository;
        this.userHelper = userHelper;
    }

    public Product create(Product product) {
        product.setOwner(userHelper.getCurrentUser());
        productRepository.save(product);
        return product;
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findByOwnerId(userHelper.getCurrentUser().getId(), pageable);
    }

    public Page<Product> searchProducts(Pageable pageable) {
        return productRepository.findByOwnerIdNot(userHelper.getCurrentUser().getId(), pageable);
    }

}
