package com.example.api1.services;

import com.example.api1.models.Product;
import com.example.api1.repositories.ProductRepository;
import com.example.api1.utils.InternalServerErrorGenerator;
import com.example.api1.utils.OkGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    private Logger logger = LoggerFactory.getLogger(ProductService.class);
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity createProduct(Product product) {
        ResponseEntity result;

        try {
            Product savedProduct = productRepository.save(product);
            result = OkGenerator.generate(Arrays.asList(savedProduct));
        } catch (Exception e) {
            result = InternalServerErrorGenerator.generate(e);
        }
        return result;
    }

    public ResponseEntity getProductByNameLike(String keyword) {
        ResponseEntity result;

        try {
            List<Product> allByDescriptionContaining = productRepository.findAllByDescriptionContaining(keyword);
            result = OkGenerator.generate(allByDescriptionContaining);
        } catch (Exception e) {
            result = InternalServerErrorGenerator.generate(e);
        }
        return result;
    }
}
