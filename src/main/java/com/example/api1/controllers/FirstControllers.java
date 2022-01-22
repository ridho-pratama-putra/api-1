package com.example.api1.controllers;

import com.example.api1.models.Blog;
import com.example.api1.models.CustomHttpError;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;
import java.util.Arrays;

@Controller
public class FirstControllers {

    @GetMapping(path = "/first")
    public ResponseEntity first() {
        return new ResponseEntity("hello  first", HttpStatus.OK);
    }

    @RolesAllowed("user_api_1")
    @GetMapping(path = "/sec")
    public ResponseEntity second() {
        Blog boo1 = new Blog("title 1", "content 1");
        Blog boo2 = new Blog("title 2", "content 2");
        CustomHttpResponse customHttpResponse = new CustomHttpResponse();
        CustomHttpStatus customHttpStatus = new CustomHttpStatus();
        customHttpStatus.setCode("00");
        customHttpStatus.setDescription("Success");

        customHttpResponse.setStatus(customHttpStatus);
        customHttpResponse.setResult(Arrays.asList(boo1, boo2));

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
