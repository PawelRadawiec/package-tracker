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
        switch (product.getCategory()) {
            case "PC":
                product.setPictureAddress("https://allegro.stati.pl/AllegroIMG/PRODUCENCI/DELL/Latitude-5401/Dell-Latitude-5401_4.jpg");
                break;
            case "PHONE":
                product.setPictureAddress("https://www.lg.com/ae/images/mobile-phones/md05875115/gallery/LGH930DS-V30-Desktop-01-05092017.jpg");
                break;
            case "HEADPHONE":
                product.setPictureAddress("https://thumbs.static-thomann.de/thumb/orig/pics/bdb/482802/15092208_800.jpg");
        }
        productRepository.save(product);
        return product;
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        return  productRepository.findById(id).orElseGet(Product::new);
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findByOwnerId(userHelper.getCurrentUser().getId(), pageable);
    }

    public Page<Product> searchProducts(ProductListRequest listRequest, Pageable pageable) {
        listRequest.setOwnerId(userHelper.getCurrentUser().getId());
        return productRepository.findAll(listSpecification.productsOwnerNotIn(listRequest), pageable);
    }

}
