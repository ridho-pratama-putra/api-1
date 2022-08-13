package com.example.api1.controllers;

import com.example.api1.enumeration.UserMessageStatus;
import com.example.api1.models.*;
import com.example.api1.repositories.UserMessageRepository;
import com.example.api1.services.UserMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.security.RolesAllowed;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class FirstControllers {
    @Autowired
    UserMessageService userMessageService;

    private Logger logger = LoggerFactory.getLogger(FirstControllers.class);

    @GetMapping(path = "/first")
    public ResponseEntity first() {
        ResponseEntity result = userMessageService.save();
        return result;
    }

    @RolesAllowed({"user_api_1"})
    @GetMapping(path = "/sec")
    public ResponseEntity second() {
        logger.info("controller /sec called");
//        ResponseEntity result = userMessageService.save();
        ResponseEntity result = userMessageService.getByStatus();
        return result;
    }

    @RolesAllowed({"user_api_1"})
    @GetMapping(path = "/getAll")
    public ResponseEntity getUserMessages() {
        logger.info("controller /sec called");
        ResponseEntity result = userMessageService.getAll();
        return result;
    }

    @RolesAllowed("user_api_1")
    @GetMapping(path = "/third")
    public ResponseEntity third() {
        CustomHttpResponse customHttpResponse = new CustomHttpResponse();
        CustomHttpStatus build = CustomHttpStatus.builder()
                .code("06")
                .description("General Error")
                .build();

        CustomHttpError customHttpError = new CustomHttpError();
        customHttpError.setSource("MS-API 1");

        CustomHttpError customHttpError2 = new CustomHttpError();
        customHttpError.setSource("MS-API 2");

        customHttpResponse.setStatus(build);
        customHttpResponse.setError(Arrays.asList(customHttpError, customHttpError2));

        return new ResponseEntity(customHttpResponse, HttpStatus.OK);
    }

    @RolesAllowed("user_api_1")
    @PostMapping(path = "/message")
    public ResponseEntity createMessage(@RequestBody UserMessage userMessage) {
        ResponseEntity responseEntity = userMessageService.create(userMessage);
        return responseEntity;
    }

    @Autowired
    UserMessageRepository userMessageRepository;

    @QueryMapping(name = "userMessages") // referenced to graphql query schema
    public List<UserMessage> gettingUserMessages() {
        return userMessageRepository.findAll();
    }

    @MutationMapping
    public UserMessage writeMessage(@Argument UserMessageInput userMessageInput) throws JsonProcessingException {
        logger.info(String.format("userMessageInput [{}]", userMessageInput));
        UserMessage build = UserMessage.builder()
                .message(userMessageInput.getMessage())
                .name(userMessageInput.getName())
                .createdDate(new Date())
                .lastModifiedDate(new Date())
                .status(UserMessageStatus.PENDING)
                .build();
        UserMessage save = userMessageRepository.save(build);
        return save   ;
    }

    @GetMapping(path = "/deleteAll")
    public ResponseEntity deleteAll() {
        userMessageRepository.deleteAllInBatch();
        return new ResponseEntity(new CustomHttpResponse(), HttpStatus.OK);
    }
}
