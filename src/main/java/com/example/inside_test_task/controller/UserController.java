package com.example.inside_test_task.controller;

import com.example.inside_test_task.dto.in.JWTRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController {
    @PostMapping("/login")
    ResponseEntity<?> getJwtFromNameAndPassword(@RequestBody JWTRequest request);
}
