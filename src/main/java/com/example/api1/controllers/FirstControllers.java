package com.example.api1.controllers;

import com.example.api1.models.CustomHttpError;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.models.UserMessage;
import com.example.api1.services.UserMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
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
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
public class FirstControllers {
    @Autowired
    UserMessageService userMessageService;

    @Autowired
    private HttpServletRequest request;

    private Logger logger = LoggerFactory.getLogger(FirstControllers.class);

    ObjectMapper objectMapper = new ObjectMapper();
    @GetMapping(path = "/first")
    public ResponseEntity first() {
        ResponseEntity result = userMessageService.save();
        return result;
    }

    @RolesAllowed({"user_api_1", "user-api-1-realm-role"})
    @GetMapping(path = "/sec")
    public ResponseEntity second() {
        logger.info("controller /sec called");
//        ResponseEntity result = userMessageService.save();
        ResponseEntity result = userMessageService.getByStatus();
        return result;
    }

    @RolesAllowed({"user_api_1", "user-api-1-realm-role"})
    @GetMapping(path = "/getAll")
    public ResponseEntity getAll() throws JsonProcessingException {
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
}
