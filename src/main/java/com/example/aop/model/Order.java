package com.example.aop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String description;
    @Enumerated
    private Status status;

    @ManyToOne
    @JoinColumn(name= "user_id", nullable = false)
    private User user;

    public enum Status {
        NEW, PROCESSING, CANCELLED, DELIVERED, RETURNED;
    }
}
