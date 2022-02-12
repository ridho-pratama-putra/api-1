package com.example.api1.utils;

import com.example.api1.models.CustomHttpError;
import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

public class InternalServerErrorGenerator {

    public static ResponseEntity generate(Exception e) {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();

        httpStatusBuilder.code("06");
        httpStatusBuilder.description("General error");
        httpResponseBuilder.status(httpStatusBuilder.build());
        CustomHttpError buildedError = CustomHttpError.builder()
                .source("api-1")
                .message(e.getClass().getSimpleName())
                .build();
        httpResponseBuilder.error(Arrays.asList(buildedError));
        return new ResponseEntity(httpResponseBuilder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
