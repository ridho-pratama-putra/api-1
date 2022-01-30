package com.example.api1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomHttpResponse {
    CustomHttpStatus status;
    List<CustomHttpError> error;
    List<Object> result;

    public CustomHttpStatus getStatus() {
        return status;
    }

    public void setStatus(CustomHttpStatus status) {
        this.status = status;
    }

    public List<CustomHttpError> getError() {
        return error;
    }

    public void setError(List<CustomHttpError> error) {
        this.error = error;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }
}
