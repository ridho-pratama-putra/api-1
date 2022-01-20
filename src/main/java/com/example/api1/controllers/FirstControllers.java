package com.example.api1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class FirstControllers {

    @GetMapping(path = "/first")
    public ResponseEntity first() {
        return new ResponseEntity("hello  first", HttpStatus.OK);
    }

    @RolesAllowed("user")
    @GetMapping(path = "/sec")
    public ResponseEntity second() {
        return new ResponseEntity("hello  second", HttpStatus.OK);
    }
}
