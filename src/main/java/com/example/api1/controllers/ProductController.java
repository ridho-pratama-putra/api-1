package com.example.api1.controllers;

import com.example.api1.models.Product;
import com.example.api1.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.security.RolesAllowed;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;
    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping(path = "/product")
//    @RolesAllowed("user_api_1")
    public ResponseEntity createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping(path = "/product")
    public ResponseEntity getAllProduct() {
        return productService.getAllProduct();
    }
}
