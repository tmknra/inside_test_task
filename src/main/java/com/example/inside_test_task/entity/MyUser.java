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
    @ToString.Exclude
    private String password;

    @OneToMany(mappedBy = "userId",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<MyMessage> myMessages;

}
