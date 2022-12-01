package com.example.inside_test_task.controller;

import com.example.inside_test_task.InsideTestTaskAppTests;
import com.example.inside_test_task.dto.in.JWTRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class UserControllerTest extends InsideTestTaskAppTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Для тестов контроллера используется библиотека Mockito.
     * Проверяются как базовые случаи, так и случаи с получением некорректной информации.
     */
    @Test
    void getJwtFromNameAndPassword() throws Exception {
        JWTRequest test = JWTRequest.builder().name("test")
                .password("123456")
                .build();
        JWTRequest invalidPassword = JWTRequest.builder().name("test")
                .password("qwerty")
                .build();
        JWTRequest notExistingUser = JWTRequest.builder().name("harry potter")
                .password("qwerty")
                .build();
        String json = objectMapper.writeValueAsString(test);
        String invalidPassJson = objectMapper.writeValueAsString(invalidPassword);
        String notExistingUserJson = objectMapper.writeValueAsString(notExistingUser);
        /**
         * Базовый случай. Пароль корректен, юзер существует.
         */
        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").isNotEmpty());
        /**
         * Случай с некорректно введенным паролем.
         */
        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPassJson))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invalid password"));
        /**
         * Случай с неверным именем юзера.
         */
        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notExistingUserJson))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Provided user not found"));
    }
}