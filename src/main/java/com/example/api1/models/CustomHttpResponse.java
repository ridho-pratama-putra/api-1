package com.example.api1.models;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomHttpResponse {
    CustomHttpStatus status;
    List<CustomHttpError> error;
    List<Object> result;
}
