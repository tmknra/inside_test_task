package com.example.inside_test_task.service;

import org.springframework.http.ResponseEntity;

public interface MessageService {
    ResponseEntity<?> getMessageFromClient(String username, String message);
}
