package com.example.inside_test_task.controller;

import com.example.inside_test_task.dto.in.MessageRequest;
import com.example.inside_test_task.exception.InvalidTokenException;
import com.example.inside_test_task.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/messages")
public interface MessageController {

    /**
     Эндпоинт для обработки сообщений, полученных от клиента
     */
    @PostMapping("/send")
    ResponseEntity<?> getMessageFromClient(@RequestBody MessageRequest request,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String header)
            throws UserNotFoundException, InvalidTokenException;
}
