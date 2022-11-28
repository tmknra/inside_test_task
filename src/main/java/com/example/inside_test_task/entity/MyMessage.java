package com.example.inside_test_task.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "messages")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    @ToString.Exclude
    private MyUser userId;

    @Column
    private String message;

    public MyMessage(MyUser name, String message) {
        this.userId = name;
        this.message = message;
    }
}
