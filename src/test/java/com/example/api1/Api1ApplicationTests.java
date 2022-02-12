package com.example.api1;

import com.example.api1.controllers.FirstControllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class Api1ApplicationTests {

    @Autowired
    private FirstControllers controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

}
