package com.example.api1.repositories;

import com.example.api1.models.UserMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMessageRepositoryTest {

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Test
    public void givenUserMessageRepository_whenSaveAndRetreiveEntity_thenOK() {
        UserMessage userMessage = userMessageRepository.save(UserMessage.builder().status("pending").message("hello").name("brown").build());
        Optional<UserMessage> foundEntity = userMessageRepository.findById(userMessage.getId());

        assertNotNull(foundEntity);
        assertEquals(userMessage.getMessage(), foundEntity.get().getMessage());
    }
}
