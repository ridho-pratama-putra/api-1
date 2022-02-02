package com.example.api1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomHttpStatus {
    String code;
    String description;
}
