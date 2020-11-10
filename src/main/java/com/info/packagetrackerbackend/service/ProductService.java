package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.ProductListRequest;
import com.info.packagetrackerbackend.service.repository.ProductRepository;
import com.info.packagetrackerbackend.service.repository.specification.ProductListSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private SystemUserHelper userHelper;
    private ProductListSpecification listSpecification;

    public ProductService(ProductRepository productRepository, SystemUserHelper userHelper, ProductListSpecification listSpecification) {
        this.productRepository = productRepository;
        this.userHelper = userHelper;
        this.listSpecification = listSpecification;
    }

    public Product create(Product product) {
        product.setOwner(userHelper.getCurrentUser());
        productRepository.save(product);
        return product;
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findByOwnerId(userHelper.getCurrentUser().getId(), pageable);
    }

    public Page<Product> searchProducts(ProductListRequest listRequest, Pageable pageable) {
        listRequest.setOwnerId(userHelper.getCurrentUser().getId());
        return productRepository.findAll(listSpecification.productsOwnerNotIn(listRequest), pageable);
    }

}
