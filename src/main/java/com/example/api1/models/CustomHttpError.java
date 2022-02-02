package com.example.api1.models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomHttpError {
    String source;
    String code;
    String message;
}
