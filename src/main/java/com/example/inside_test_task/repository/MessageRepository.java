package com.example.inside_test_task.repository;

import com.example.inside_test_task.entity.MyMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MyMessage, Long> {

    @Query(value = "SELECT * FROM messages ORDER BY id DESC LIMIT amount=?", nativeQuery = true)
    Page<MyMessage> findLastMessages(@Param("amount") Integer amount);
}
