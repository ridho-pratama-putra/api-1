package com.example.api1.controllers;

import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.models.Product;
import com.example.api1.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductController productController;

    @Test
    public void createProduct_shouldCallProductService() throws Exception {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        Product savedProduct = Product.builder().description("dummy").barcode("hello dummy").sellPrice("10000").stockAvailable(4).build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Arrays.asList(savedProduct));
        ObjectMapper objectMapper = new ObjectMapper();
        String expectation = objectMapper.writeValueAsString(httpResponseBuilder.build());
        String content = objectMapper.writeValueAsString(savedProduct);

        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(new ResponseEntity(httpResponseBuilder.build(), HttpStatus.OK));
        this.mockMvc.perform(post("/products")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectation));
    }

    @Test
    public void getAllProduct_shouldCallProductService() throws Exception {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        Product savedProduct = Product.builder().id(1L).description("dummy").barcode("hello dummy").sellPrice("10000").lastModifiedDate(new Date()).createdDate(new Date()).stockAvailable(4).build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Arrays.asList(savedProduct));

        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(new ResponseEntity(httpResponseBuilder.build(), HttpStatus.OK));
        this.mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                ;
    }

    @Test
    public void get_shouldCallProductServiceGetProductNameLike_whenCall() throws Exception {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        Product savedProduct = Product.builder().id(1L).description("dummy").barcode("hello dummy").sellPrice("10000").lastModifiedDate(new Date()).createdDate(new Date()).stockAvailable(4).build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Arrays.asList(savedProduct));

        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(new ResponseEntity(httpResponseBuilder.build(), HttpStatus.OK));
        this.mockMvc.perform(get("/products/search?name=du"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void updateProduct_shouldCallProductService() throws Exception {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        Product savedProduct = Product.builder().id(1L).description("dummy").barcode("hello dummy").sellPrice("10000").stockAvailable(4).build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Arrays.asList(savedProduct));
        ObjectMapper objectMapper = new ObjectMapper();
        String expectation = objectMapper.writeValueAsString(httpResponseBuilder.build());
        String content = objectMapper.writeValueAsString(savedProduct);
        Mockito.when(productService.updateProduct(Mockito.any(), Mockito.any())).thenReturn(new ResponseEntity(httpResponseBuilder.build(), HttpStatus.OK));

        this.mockMvc.perform(put("/products/1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectation));
        Mockito.verify(productService, Mockito.times(1)).updateProduct(Mockito.anyLong(), Mockito.any());
    }

    @Test
    public void deleteProduct_shouldCallProductService() throws Exception {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        Product savedProduct = Product.builder().id(1L).description("dummy").barcode("hello dummy").sellPrice("10000").stockAvailable(4).build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Arrays.asList(savedProduct));
        Mockito.when(productService.updateProduct(Mockito.any(), Mockito.any())).thenReturn(new ResponseEntity(httpResponseBuilder.build(), HttpStatus.OK));

        this.mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
        Mockito.verify(productService, Mockito.times(1)).deleteProduct(Mockito.anyLong());
    }
}
