package com.example.api1.controllers;

import com.example.api1.models.Product;
import com.example.api1.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(path = "/products/{productId}/sold")
    public ResponseEntity decrementProductStock(@PathVariable Long productId) {
        return productService.decrementStock(productId, 1);
    }

    @DeleteMapping(path = "/products/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

}
