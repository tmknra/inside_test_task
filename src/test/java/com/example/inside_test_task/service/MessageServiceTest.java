package com.example.inside_test_task.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.inside_test_task.InsideTestTaskAppTests;
import com.example.inside_test_task.dto.in.MessageRequest;
import com.example.inside_test_task.exception.InvalidTokenException;
import com.example.inside_test_task.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageServiceTest extends InsideTestTaskAppTests {
    @Autowired
    private MessageService messageService;
    /**
     Для тестов сервиса используется библиотека JUnit.
     Проверяются как базовые случаи, так и случаи с получением некорректной информации.
     */
    @Test
    void getMessageFromClient() throws UserNotFoundException, InvalidTokenException {
        MessageRequest message1 = MessageRequest.builder().name("test").message("hello").build();
        String token1 = JWT.create().withSubject(message1.getName()).sign(Algorithm.HMAC256("secret".getBytes()));
        String invalidToken =
                JWT.create().withSubject(message1.getName()+"qweqwe").sign(Algorithm.HMAC256("secret".getBytes()));

        MessageRequest message2 = MessageRequest.builder().name("test").message("history 3").build();
        String token2 = JWT.create().withSubject(message2.getName()).sign(Algorithm.HMAC256("secret".getBytes()));

        MessageRequest notExistingUser = MessageRequest.builder().name("Justin Bieber").message("history 3").build();
        String token3 = JWT.create().withSubject(notExistingUser.getName()).sign(Algorithm.HMAC256("secret".getBytes()));


        /**
         Базовый случай
         */
        HashMap<String, Object> messageFromClient1 =
                messageService.getMessageFromClient(message1.getName(), message1.getMessage(), "Bearer_" + token1);

        assertEquals("Message successfully added to database.", messageFromClient1.get("message"));
        /**
         Случай с запросом истории сообщений
         */
        HashMap<String, Object> messageFromClient2 =
                messageService.getMessageFromClient(message2.getName(), message2.getMessage(), "Bearer_" + token2);
        assertEquals(ArrayList.class, messageFromClient2.get("message").getClass());

        /**
         Случай с некорректным токеном. Проверяем выбрасывается ли исключение.
         */
        assertThrows(InvalidTokenException.class, () -> messageService.getMessageFromClient(
                message1.getName(), message1.getMessage(),"Bearer_" + invalidToken));
        /**
         Случай с некорректным именем юзера. Проверяем выбрасывается ли исключение.
         */
        assertThrows(UserNotFoundException.class, () -> messageService.getMessageFromClient(
                notExistingUser.getName(), notExistingUser.getMessage(),"Bearer_" + token3));
    }
}