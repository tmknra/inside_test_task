package com.example.inside_test_task.controller;

import com.example.inside_test_task.dto.in.MessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/messages")
public interface MessageController {
    @PostMapping("/send")
    ResponseEntity<?> getMessageFromClient(@RequestBody MessageRequest request);
}
