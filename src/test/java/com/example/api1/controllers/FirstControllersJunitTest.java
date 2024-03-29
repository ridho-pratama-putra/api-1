package com.example.api1.controllers;

import com.example.api1.enumeration.UserMessageStatus;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.models.UserMessage;
import com.example.api1.services.UserMessageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class FirstControllersJunitTest {

    @Mock
    UserMessageService userMessageService;

    @Before
    public void createMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    FirstControllers firstControllers;

    @Test
    public void first_shouldReturnWhatServiceSays() {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        UserMessage savedMessage = UserMessage.builder().name("dummy").message("hello dummy").status(UserMessageStatus.PENDING).build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Collections.singletonList(savedMessage));
        CustomHttpResponse expected = httpResponseBuilder.build();
        Mockito.when(userMessageService.save())
                .thenReturn(new ResponseEntity(expected, HttpStatus.OK));

        ResponseEntity result = firstControllers.first();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertTrue(expected.equals(result.getBody()));
    }

    @Test
    @Description("this test is not valid due to api call can be done without roles. but still good because it can be use as logic layer test. keycloak part likely hard to test")
    public void sec_shouldReturnWhatServiceSays() {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        UserMessage savedMessage = UserMessage.builder().name("dummy").message("hello dummy").status(UserMessageStatus.PENDING).build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Collections.singletonList(savedMessage));
        CustomHttpResponse expected = httpResponseBuilder.build();
        Mockito.when(userMessageService.getByStatus())
                .thenReturn(new ResponseEntity(expected, HttpStatus.OK));
        ResponseEntity result = firstControllers.second();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void create_shouldReturnWhatServiceSays() {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        UserMessage savedMessage = UserMessage.builder().name("dummy").message("hello dummy").status(UserMessageStatus.PENDING).build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Collections.singletonList(savedMessage));
        CustomHttpResponse expected = httpResponseBuilder.build();
        Mockito.when(userMessageService.create(Mockito.any(UserMessage.class)))
                .thenReturn(new ResponseEntity(expected, HttpStatus.CREATED));
        ResponseEntity result = firstControllers.createMessage(savedMessage);

        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
}
