package com.example.api1.models;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable =false)
    private String name;

    @Column(nullable =false)
    private String message;

    @Column(nullable =false)
    private String status;
}
