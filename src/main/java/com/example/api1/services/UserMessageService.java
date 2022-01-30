package com.example.api1.services;

import com.example.api1.controllers.FirstControllers;
import com.example.api1.models.UserMessage;
import com.example.api1.repositories.UserMessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserMessageService {
    private Logger logger = LoggerFactory.getLogger(FirstControllers.class);
    UserMessageRepository userMessageRepository;

    public UserMessageService(UserMessageRepository userMessageRepository) {
        this.userMessageRepository = userMessageRepository;
    }

    public String save() throws JsonProcessingException {
        logger.info("service save called");
        UserMessage savedMessage = userMessageRepository.save(UserMessage.builder().name("dummy").message("hello dummy").status("pending").build());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(savedMessage);
    }

}
