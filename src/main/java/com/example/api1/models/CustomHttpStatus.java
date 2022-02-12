package com.example.api1.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class CustomHttpStatus {
    String code;
    String description;
}
