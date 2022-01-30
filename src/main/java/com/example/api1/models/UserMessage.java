package com.example.api1.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =false)
    private String name;

    @Column(nullable =false)
    private String message;

    @Column(nullable =false)
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMessage that = (UserMessage) o;
        return name.equals(that.name) &&
                message.equals(that.message) &&
                status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, message, status);
    }
}
