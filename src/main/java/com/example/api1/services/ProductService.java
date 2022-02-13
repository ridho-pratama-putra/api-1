package com.example.api1.services;

import com.example.api1.Exceptions.ProductNotFoundException;
import com.example.api1.models.Product;
import com.example.api1.repositories.ProductRepository;
import com.example.api1.utils.InternalServerErrorGenerator;
import com.example.api1.utils.OkGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ResponseEntity updateProduct(Product product) {
        ResponseEntity result;

        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        Product retrievedProduct = optionalProduct.get();
        retrievedProduct.setBarcode(product.getBarcode());
        retrievedProduct.setDescription(product.getDescription());
        retrievedProduct.setSellPrice(product.getSellPrice());
        Product contentResult = productRepository.save(retrievedProduct);
        result = OkGenerator.generate(Arrays.asList(contentResult));
        return result;
    }

    public ResponseEntity deleteProduct(Product product) {
        ResponseEntity result;
        Optional<Product> byId = productRepository.findById(product.getId());
        if (byId.isPresent()) {
            productRepository.delete(product);
            result = OkGenerator.generate(Collections.EMPTY_LIST);
            return result;
        }
        return InternalServerErrorGenerator.generate(new ProductNotFoundException("product not found"));
    }

    public ResponseEntity incrementStock(Long id, Integer incrementQuantity) {
        Product product = productRepository.findById(id).get();
        product.setStockAvailable(product.getStockAvailable() + incrementQuantity);
        product.setLastModifiedDate(new Date());
        Product updatedProduct = productRepository.save(product);
        return OkGenerator.generate(Collections.singletonList(updatedProduct));
    }

    public ResponseEntity decrementStock(Long id, Integer decrementQuantity) {
        Product product = productRepository.findById(id).get();
        product.setStockAvailable(product.getStockAvailable() + decrementQuantity);
        product.setLastModifiedDate(new Date());
        Product updatedProduct = productRepository.save(product);
        return OkGenerator.generate(Collections.singletonList(updatedProduct));
    }
}
