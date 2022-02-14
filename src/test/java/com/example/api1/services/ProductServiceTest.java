package com.example.api1.services;

import com.example.api1.models.CustomHttpError;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.models.Product;
import com.example.api1.repositories.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

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
    @DisplayName("product doesnt have price")
    public void createProduct_shouldReturnInternalServerError_whenSaveToDatabaseThrowingException() {
        Product product = Product.builder()
                .description("Sabun Lifebuoy kuning 80gr")
                .barcode("07615207546125476")
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
    public void getProductByNameLike_shouldReturnMostLikelyProduct_whenCalledWithSa() {
        List<Product> expectedMatchedProduct = Arrays.asList(Product.builder().barcode("254367890").description("sabun").sellPrice("10000").build(),
                Product.builder().barcode("254367891").description("sampo").sellPrice("10000").build());
        Mockito.when(productRepository.findAllByDescriptionContaining(Mockito.any(String.class))).thenReturn(expectedMatchedProduct);

        ResponseEntity result = productService.getProductByNameLike("sa");

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void updateProduct_shouldDoUpdate_whenCalledWithPriceChanges() {
        Product sabun = Product.builder().id(1L).barcode("254367890").description("sabun").sellPrice("10000").build();
        Product latestProduct = Product.builder().id(1L).barcode("254367890").description("sabun").sellPrice("15000").build();
        Mockito.when(productRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(sabun));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(latestProduct);
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Collections.singletonList(latestProduct));
        CustomHttpResponse expected = httpResponseBuilder.build();

        ResponseEntity result = productService.updateProduct(latestProduct);

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertTrue(expected.getStatus().equals(body.getStatus()));
        Assert.assertTrue(expected.getResult().equals(body.getResult()));
    }

    @Test
    public void incrementStock_shouldDoUpdateStock_whenCalled() {
        Product sabun = Product.builder().id(1L).barcode("254367890").description("sabun").sellPrice("10000")
                .stockAvailable(10)
                .build();
        Product addSabun = Product.builder().id(1L).barcode("254367890").description("sabun").sellPrice("10000")
                .stockAvailable(11)
                .build();
        Mockito.when(productRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(sabun));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(addSabun);
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Collections.singletonList(addSabun));
        CustomHttpResponse expected = httpResponseBuilder.build();

        ResponseEntity result = productService.incrementStock(1L, 1);

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertTrue(expected.getStatus().equals(body.getStatus()));
        Assert.assertTrue(expected.getResult().equals(body.getResult()));
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void decrementStock_shouldDoUpdateStock_whenCalled() {
        Product sabun = Product.builder().id(1L).barcode("254367890").description("sabun").sellPrice("10000")
                .stockAvailable(10)
                .build();
        Product addSabun = Product.builder().id(1L).barcode("254367890").description("sabun").sellPrice("10000")
                .stockAvailable(9)
                .build();
        Mockito.when(productRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(sabun));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(addSabun);
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Collections.singletonList(addSabun));
        CustomHttpResponse expected = httpResponseBuilder.build();

        ResponseEntity result = productService.decrementStock(1L, 1);

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertTrue(expected.getStatus().equals(body.getStatus()));
        Assert.assertTrue(expected.getResult().equals(body.getResult()));
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void deleteProduct_shouldDoDelete_whenProductExist() {
        Product sabun = Product.builder().id(1L).barcode("254367890").description("sabun").sellPrice("10000").build();
        Mockito.when(productRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(sabun));
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Collections.emptyList());
        CustomHttpResponse expected = httpResponseBuilder.build();

        ResponseEntity result = productService.deleteProduct(sabun);

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertTrue(expected.getStatus().equals(body.getStatus()));
        Assert.assertTrue(expected.getResult().equals(body.getResult()));
    }

    @Test
    public void deleteProduct_shouldThrowException_whenProductNotFound() {
        Product sabun = Product.builder().id(1L).barcode("254367890").description("sabun").sellPrice("10000").build();
        Mockito.when(productRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.<Product>empty());
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("06");
        httpStatusBuilder.description("General error");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.error(Collections.singletonList(new CustomHttpError("api-1", null, "ProductNotFoundException")));
        CustomHttpResponse expected = httpResponseBuilder.build();

        ResponseEntity result = productService.deleteProduct(sabun);

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        Assert.assertTrue(expected.getStatus().equals(body.getStatus()));
        Assert.assertTrue(expected.getError().equals(body.getError()));
    }

    @Test
    public void getAllProduct_shouldReturnAllProducts_whenCalled() {
        List<Product> expectedMatchedProduct = Arrays.asList(Product.builder().barcode("254367890").description("sabun").sellPrice("10000").build(),
                Product.builder().barcode("254367891").description("sampo").sellPrice("10000").build());
        Mockito.when(productRepository.findAll()).thenReturn(expectedMatchedProduct);

        ResponseEntity result = productService.getAllProduct();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
