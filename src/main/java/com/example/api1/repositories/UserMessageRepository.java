package com.example.api1.repositories;

import com.example.api1.enumeration.UserMessageStatus;
import com.example.api1.models.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {

    List<UserMessage> findAllByStatus(UserMessageStatus userMessageStatus);
}
