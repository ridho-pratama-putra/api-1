package com.example.api1.repositories;

import com.example.api1.models.UserMessage;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMessageRepositoryTest {

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Test
    public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        UserMessage userMessage = userMessageRepository.save(UserMessage.builder().status("pending").message("hello").name("brown").build());
        Optional<UserMessage> foundEntity = userMessageRepository.findById(userMessage.getId());

        assertNotNull(foundEntity);
        assertEquals(userMessage.getMessage(), foundEntity.get().getMessage());
    }
}
