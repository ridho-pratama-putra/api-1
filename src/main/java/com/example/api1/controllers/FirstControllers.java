package com.example.api1.controllers;

import com.example.api1.models.CustomHttpError;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.services.UserMessageService;
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
