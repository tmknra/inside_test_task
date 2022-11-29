package com.example.inside_test_task.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.inside_test_task.InsideTestTaskApplicationTests;
import com.example.inside_test_task.dto.in.MessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class MessageControllerTest extends InsideTestTaskApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMessageFromClient() throws Exception {
        MessageRequest test1 = MessageRequest.builder()
                .name("test")
                .message("Hello!")
                .build();
        MessageRequest test2 = MessageRequest.builder()
                .name("test")
                .message("history 5")
                .build();
        MessageRequest notExistingUser = MessageRequest.builder()
                .name("test5")
                .message("history 5")
                .build();


        String token1 = JWT.create().withSubject(test1.getName()).sign(Algorithm.HMAC256("secret".getBytes()));
        String token2 = JWT.create().withSubject(test2.getName()).sign(Algorithm.HMAC256("secret".getBytes()));
        String token3 = JWT.create().withSubject(notExistingUser.getName()).sign(Algorithm.HMAC256("secret".getBytes()));
        String invalidToken =
                JWT.create().withSubject(test1.getName()+"asd").sign(Algorithm.HMAC256("secret".getBytes()));

        String json1 = objectMapper.writeValueAsString(test1);
        String json2 = objectMapper.writeValueAsString(test2);
        String json3 = objectMapper.writeValueAsString(notExistingUser);

        this.mockMvc.perform(post("/messages/send")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer_"+token1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Message successfully added to database."));

        this.mockMvc.perform(post("/messages/send")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer_"+token2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").isArray());

        this.mockMvc.perform(post("/messages/send")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer_"+token3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json3))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Provided user not found"));

        this.mockMvc.perform(post("/messages/send")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer_"+invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid token"));

    }
}