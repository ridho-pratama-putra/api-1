package com.example.api1.repositories;

import com.example.api1.enumeration.UserMessageStatus;
import com.example.api1.models.UserMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserMessageRepositoryTest {

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Test
    public void givenUserMessageRepository_whenSaveAndRetreiveEntity_thenOK() {
        UserMessage userMessage = userMessageRepository.save(UserMessage.builder().status(UserMessageStatus.PENDING).message("hello").name("brown").createdDate(new Date()).lastModifiedDate(new Date()).build());
        Optional<UserMessage> foundEntity = userMessageRepository.findById(userMessage.getId());

        assertNotNull(foundEntity);
        assertEquals(userMessage.getMessage(), foundEntity.get().getMessage());
    }
}
