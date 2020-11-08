package com.info.packagetrackerbackend.controller;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.ProductListRequest;
import com.info.packagetrackerbackend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/product/")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "create")
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.create(product), HttpStatus.OK);
    }

    @GetMapping(value = "products")
    public ResponseEntity<Page<Product>> products(Pageable pageable) {
        return new ResponseEntity<>(productService.getProducts(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "search")
    public ResponseEntity<Page<Product>> search(ProductListRequest request, Pageable pageable) {
        return new ResponseEntity<>(productService.searchProducts(request, pageable), HttpStatus.OK);
    }

}
