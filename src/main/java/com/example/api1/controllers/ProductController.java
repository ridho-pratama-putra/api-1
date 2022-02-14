package com.example.api1.controllers;

import com.example.api1.models.Product;
import com.example.api1.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;
    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping(path = "/products")
//    @RolesAllowed("user_api_1")
    public ResponseEntity createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping(path = "/products")
    public ResponseEntity getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping(path = "/products/search")
    public ResponseEntity get(@RequestParam(name="name", required = true) String productName) {
        return productService.getProductByNameLike(productName);
    }

    @PutMapping(path = "/products/{productId}")
    public ResponseEntity updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        return productService.updateProduct(productId, product);
    }
}