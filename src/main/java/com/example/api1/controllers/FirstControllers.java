package com.example.api1.controllers;

import com.example.api1.models.CustomHttpError;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.services.UserMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;
import java.util.Arrays;

@Controller
public class FirstControllers {
    UserMessageService userMessageService;
    private Logger logger = LoggerFactory.getLogger(FirstControllers.class);

    public FirstControllers(UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
    }

    @GetMapping(path = "/first")
    public ResponseEntity first() {
        try {
            userMessageService.save();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity("ll", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity("hello  first", HttpStatus.OK);
    }

    @RolesAllowed("user_api_1")
    @GetMapping(path = "/sec")
    public ResponseEntity second() throws JsonProcessingException {
        logger.info("controller /sec called");
        CustomHttpResponse customHttpResponse = new CustomHttpResponse();
        CustomHttpStatus customHttpStatus = new CustomHttpStatus();
        customHttpStatus.setCode("00");
        customHttpStatus.setDescription("Success");

        String save = userMessageService.save();

        customHttpResponse.setStatus(customHttpStatus);
        customHttpResponse.setResult(Arrays.asList(save));

        return new ResponseEntity(customHttpResponse, HttpStatus.OK);
    }

    @RolesAllowed("user_api_1")
    @GetMapping(path = "/third")
    public ResponseEntity third() {
        CustomHttpResponse customHttpResponse = new CustomHttpResponse();
        CustomHttpStatus customHttpStatus = new CustomHttpStatus();
        customHttpStatus.setCode("06");
        customHttpStatus.setDescription("General Error");

        CustomHttpError customHttpError = new CustomHttpError();
        customHttpError.setSource("MS-API 1");

        CustomHttpError customHttpError2 = new CustomHttpError();
        customHttpError.setSource("MS-API 2");

        customHttpResponse.setStatus(customHttpStatus);
        customHttpResponse.setError(Arrays.asList(customHttpError, customHttpError2));

        return new ResponseEntity(customHttpResponse, HttpStatus.OK);
    }
}
