package com.example.inside_test_task.service;

import com.auth0.jwt.JWT;
import com.example.inside_test_task.InsideTestTaskAppTests;
import com.example.inside_test_task.dto.in.JWTRequest;
import com.example.inside_test_task.exception.InvalidPasswordException;
import com.example.inside_test_task.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest extends InsideTestTaskAppTests {
    @Autowired
    private UserService userService;
    /**
     * Для тестов сервиса используется библиотека JUnit.
     * Проверяются как базовые случаи, так и случаи с получением некорректной информации.
     */
    @Test
    void getJwtFromNameAndPassword() throws UserNotFoundException, InvalidPasswordException {
        JWTRequest test = JWTRequest.builder().name("test").password("123456").build();
        /**
         * Базовый случай. Имя и пароль верны.
         */
        HashMap<String, String> map = userService.getJwtFromNameAndPassword(test);
        assertTrue(map.containsKey("token"));
        assertEquals("test", JWT.decode(map.get("token")).getSubject());
        /**
         * Случай с некорректным именем юзера. Проверяем выбрасывается ли исключение.
         */
        JWTRequest notExistingUser = JWTRequest.builder().name("Dustin Hoffman").password("123456").build();
        assertThrows(UserNotFoundException.class, () -> userService.getJwtFromNameAndPassword(notExistingUser));
        /**
         * Случай с некорректным паролем. Проверяем выбрасывается ли исключение.
         */
        JWTRequest wrongPassword = JWTRequest.builder().name("test").password("1234561").build();
        assertThrows(InvalidPasswordException.class, () -> userService.getJwtFromNameAndPassword(wrongPassword));
    }
}