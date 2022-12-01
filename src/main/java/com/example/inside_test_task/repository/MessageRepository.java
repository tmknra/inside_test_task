package com.example.inside_test_task.repository;

import com.example.inside_test_task.entity.MyMessage;
import com.example.inside_test_task.repository.data.LastMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<MyMessage, Long> {
    /**
     * Кастомная очередь для БД с целью получения указанного количества последних записей.
     */
    @Query("select m.id as id, m.userId.name as name, m.message as message " +
            "from MyMessage m order by m.id desc limit :amount")
    List<LastMessages> findLastMessages(String amount);
}
