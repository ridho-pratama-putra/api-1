package com.example.api1.services;

import com.example.api1.models.CustomHttpError;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.models.Product;
import com.example.api1.repositories.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    public void createProduct_shouldSuccessCreateProduct_whenCalled() {
        Product product = Product.builder()
                .description("Sabun Lifebuoy kuning 80gr")
                .sellPrice("3000")
                .barcode("07615207546125476")
                .createdDate(new Date())
                .lastModifiedDate(new Date())
                .build();
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Collections.singletonList(product));
        CustomHttpResponse expected = httpResponseBuilder.build();
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ResponseEntity result = productService.createProduct(product);

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertTrue(expected.getStatus().equals(body.getStatus()));
        Assert.assertTrue(expected.getResult().equals(body.getResult()));
    }

    @Test
    @Description("product doesnt have prive")
    public void createProduct_shouldReturnInternalServerError_whenSaveToDatabaseThrowingException() {
        Product product = Product.builder()
                .description("Sabun Lifebuoy kuning 80gr")
                .barcode("07615207546125476")
                .createdDate(new Date())
                .lastModifiedDate(new Date())
                .build();
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("06");
        httpStatusBuilder.description("General error");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.error(Arrays.asList(CustomHttpError.builder().source("api-1").message("DataIntegrityViolationException").build()));
        CustomHttpResponse expected = httpResponseBuilder.build();
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenThrow(DataIntegrityViolationException.class);

        ResponseEntity result = productService.createProduct(product);

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        Assert.assertTrue(expected.getStatus().equals(body.getStatus()));
        Assert.assertTrue(expected.getError().equals(body.getError()));
    }

    @Test
    public void getProductByNameLike_shouldReturnMostLikelyProduct_whenCalledWithSabun() {
        List<Product> expectedMatchedProduct = Arrays.asList(productRepository.save(Product.builder().barcode("254367890").description("sabun").sellPrice("10000").createdDate(new Date()).lastModifiedDate(new Date()).build()),
                productRepository.save(Product.builder().barcode("254367891").description("sampo").sellPrice("10000").createdDate(new Date()).lastModifiedDate(new Date()).build()));
        Mockito.when(productRepository.findAllByDescriptionContaining(Mockito.any(String.class))).thenReturn(expectedMatchedProduct);

        ResponseEntity result = productService.getProductByNameLike("sa");

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
