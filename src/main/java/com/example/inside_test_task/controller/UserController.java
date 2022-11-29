package com.example.inside_test_task.controller;

import com.example.inside_test_task.dto.in.JWTRequest;
import com.example.inside_test_task.exception.InvalidPasswordException;
import com.example.inside_test_task.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface UserController {
    @PostMapping("/login")
    ResponseEntity<?> getJwtFromNameAndPassword(@RequestBody JWTRequest request)
            throws UserNotFoundException, InvalidPasswordException;
}
