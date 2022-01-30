package com.example.api1.services;

import com.example.api1.models.CustomHttpResponse;
import com.example.api1.models.CustomHttpStatus;
import com.example.api1.models.UserMessage;
import com.example.api1.repositories.UserMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserMessageService {
    private Logger logger = LoggerFactory.getLogger(UserMessageService.class);
    UserMessageRepository userMessageRepository;

    public UserMessageService(UserMessageRepository userMessageRepository) {
        this.userMessageRepository = userMessageRepository;
    }

    public CustomHttpResponse save() {
        logger.info("service save called");
        UserMessage savedMessage = userMessageRepository.save(UserMessage.builder().name("dummy").message("hello dummy").status("pending").build());
        CustomHttpResponse.CustomHttpResponseBuilder httpResponseBuilder = CustomHttpResponse.builder();
        CustomHttpStatus.CustomHttpStatusBuilder httpStatusBuilder = CustomHttpStatus.builder();
        httpStatusBuilder.code("00");
        httpStatusBuilder.description("Success");
        httpResponseBuilder.status(httpStatusBuilder.build());
        httpResponseBuilder.result(Arrays.asList(savedMessage));

        return httpResponseBuilder.build();
    }

}
