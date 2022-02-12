package com.example.api1.repositories;

import com.example.api1.models.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findyByNameLike_shouldReturnProductWithNameLikely_whenCalled() {
        productRepository.save(Product.builder().barcode("254367890").description("sabun").sellPrice("10000").createdDate(new Date()).lastModifiedDate(new Date()).build());
        productRepository.save(Product.builder().barcode("254367891").description("sampo").sellPrice("10000").createdDate(new Date()).lastModifiedDate(new Date()).build());
        productRepository.save(Product.builder().barcode("254367894").description("odol").sellPrice("10000").createdDate(new Date()).lastModifiedDate(new Date()).build());

        List<Product> result = productRepository.findAllByDescriptionContaining("sa");

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
