package com.example.inside_test_task.controller;

import com.auth0.jwt.JWT;
import com.example.inside_test_task.InsideTestTaskApplicationTests;
import com.example.inside_test_task.dto.in.JWTRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class UserControllerTest extends InsideTestTaskApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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

        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").isNotEmpty());

        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPassJson))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invalid password"));

        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notExistingUserJson))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Provided user not found"));
    }
}