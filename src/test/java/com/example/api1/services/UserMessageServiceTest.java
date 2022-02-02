package com.example.api1.services;

import com.example.api1.models.CustomHttpError;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.models.UserMessage;
import com.example.api1.repositories.UserMessageRepository;
import org.junit.Assert;
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

import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserMessageServiceTest {

    @Mock
    UserMessageRepository userMessageRepository;

    @InjectMocks
    UserMessageService userMessageService;


    @Test
    public void save_shouldReturnCreatedDummyRecord_whenSuccessCreateDummyRecord() {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        UserMessage savedMessage = UserMessage.builder()
                .name("dummy")
                .message("hello dummy")
                .status("pending")
                .build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Collections.singletonList(savedMessage));
        CustomHttpResponse expected = httpResponseBuilder.build();
        Mockito.when(userMessageRepository.save(Mockito.any(UserMessage.class))).thenReturn(savedMessage);

        ResponseEntity result = userMessageService.save();

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertTrue(expected.getStatus().equals(body.getStatus()));
        Assert.assertTrue(expected.getResult().equals(body.getResult()));
    }

    @Test
    public void create_shouldReturnCreatedRecord_whenSuccessCreatRecord() {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        UserMessage message = UserMessage.builder()
                .name("dummy")
                .message("hello dummy")
                .status("pending")
                .build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Collections.singletonList(message));
        CustomHttpResponse expected = httpResponseBuilder.build();
        Mockito.when(userMessageRepository.save(Mockito.any(UserMessage.class))).thenReturn(message);

        ResponseEntity result = userMessageService.create(message);

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertTrue(expected.getStatus().equals(body.getStatus()));
        Assert.assertTrue(expected.getResult().equals(body.getResult()));
    }

    @Test
    public void create_shouldReturnError_whenNotNullReferencesNullValued() {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("06");
        httpStatusBuilder.description("General error");
        UserMessage message = UserMessage.builder()
                .name("dummy")
                .message("hello dummy")
                .build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.error(Arrays.asList(CustomHttpError.builder().source("api-1").message("DataIntegrityViolationException").build()));
        CustomHttpResponse expected = httpResponseBuilder.build();
        Mockito.when(userMessageRepository.save(Mockito.any(UserMessage.class))).thenThrow(DataIntegrityViolationException.class);

        ResponseEntity result = userMessageService.create(message);

        CustomHttpResponse body = (CustomHttpResponse) result.getBody();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        Assert.assertTrue(expected.getStatus().equals(body.getStatus()));
        Assert.assertTrue(expected.getError().equals(body.getError()));
    }

}
