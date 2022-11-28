package com.example.inside_test_task.controller.impl;

import com.example.inside_test_task.controller.UserController;
import com.example.inside_test_task.dto.in.JWTRequest;
import com.example.inside_test_task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> getJwtFromNameAndPassword(JWTRequest request) {
        return ResponseEntity.ok(userService.getJwtFromNameAndPassword(request));
    }
}
