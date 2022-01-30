package com.example.api1.controllers;

import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.models.UserMessage;
import com.example.api1.services.UserMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FirstControllersTest {

    @MockBean
    UserMessageService userMessageService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    FirstControllers firstControllers;

    @Test
    public void first_shouldReturnOk() throws Exception {
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        UserMessage savedMessage = UserMessage.builder().name("dummy").message("hello dummy").status("pending").build();
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Arrays.asList(savedMessage));
        ObjectMapper objectMapper = new ObjectMapper();
        String expectation = objectMapper.writeValueAsString(httpResponseBuilder.build());

        Mockito.when(userMessageService.save()).thenReturn(httpResponseBuilder.build());
        this.mockMvc.perform(get("/first"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectation));
    }

    @Test
    public void first_shouldReturnInternalServerError_whenServiceThrowException() throws Exception {
        Mockito.when(userMessageService.save()).thenThrow(JsonProcessingException.class);
        this.mockMvc.perform(get("/first"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void sec_shouldReturnUnauthorized_whenRequestDoesntHaveToken() throws Exception {
        this.mockMvc.perform(get("/sec"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
