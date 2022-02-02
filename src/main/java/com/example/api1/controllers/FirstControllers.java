package com.example.api1.controllers;

import com.example.api1.models.CustomHttpError;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.models.UserMessage;
import com.example.api1.services.UserMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.security.RolesAllowed;
import java.util.Arrays;

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

    @RolesAllowed("user_api_1")
    @GetMapping(path = "/sec")
    public ResponseEntity second() {
        logger.info("controller /sec called");
        ResponseEntity result = userMessageService.save();
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
}
