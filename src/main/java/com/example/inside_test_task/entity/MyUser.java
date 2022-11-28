package com.example.inside_test_task.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String password;

    @OneToMany(mappedBy = "myUser",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private List<MyMessage> messages;
}
