package com.example.api1.services;

import com.example.api1.enumeration.UserMessageStatus;
import com.example.api1.models.CustomHttpError;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.models.UserMessage;
import com.example.api1.repositories.UserMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserMessageService {
    private Logger logger = LoggerFactory.getLogger(UserMessageService.class);
    UserMessageRepository userMessageRepository;

    public UserMessageService(UserMessageRepository userMessageRepository) {
        this.userMessageRepository = userMessageRepository;
    }

    public ResponseEntity save() {
        logger.info("service save called");
        UserMessage savedMessage = userMessageRepository.save(UserMessage.builder()
                .name("dummy")
                .message("hello dummy")
                .status(UserMessageStatus.PENDING)
                .createdDate(new Date())
                .lastModifiedDate(new Date())
                .build());
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Arrays.asList(savedMessage));

        return new ResponseEntity(httpResponseBuilder.build(), HttpStatus.OK);
    }

    public ResponseEntity getByStatus() {
        logger.info("service get called");
        List<UserMessage> all = userMessageRepository.findAllByStatus(UserMessageStatus.PENDING);
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(new ArrayList<>(all));

        return new ResponseEntity(httpResponseBuilder.build(), HttpStatus.OK);
    }

    public ResponseEntity getAll() {
        logger.info("service All called");
        List<UserMessage> all = userMessageRepository.findAll();
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(new ArrayList<>(all));

        return new ResponseEntity(httpResponseBuilder.build(), HttpStatus.OK);
    }

    public ResponseEntity create(UserMessage userMessage) {
        ResponseEntity result;
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        userMessage.setCreatedDate(new Date());
        userMessage.setLastModifiedDate(new Date());
        UserMessage savedMessage;

        try {
            savedMessage = userMessageRepository.save(userMessage);
            httpStatusBuilder.code("00");
            httpStatusBuilder.description("Success");
            httpResponseBuilder.status(httpStatusBuilder.build());
            httpResponseBuilder.result(Arrays.asList(savedMessage));
            result = new ResponseEntity(httpResponseBuilder.build(), HttpStatus.OK);
        } catch (Exception e) {
            httpStatusBuilder.code("06");
            httpStatusBuilder.description("General error");
            httpResponseBuilder.status(httpStatusBuilder.build());
            CustomHttpError buildedError = CustomHttpError.builder()
                    .source("api-1")
                    .message(e.getClass().getSimpleName())
                    .build();
            httpResponseBuilder.error(Arrays.asList(buildedError));
            result = new ResponseEntity(httpResponseBuilder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }
}
