package com.example.inside_test_task.controller.impl;

import com.example.inside_test_task.controller.UserController;
import com.example.inside_test_task.dto.in.JWTRequest;
import com.example.inside_test_task.exception.InvalidPasswordException;
import com.example.inside_test_task.exception.UserNotFoundException;
import com.example.inside_test_task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> getJwtFromNameAndPassword(JWTRequest request) throws UserNotFoundException, InvalidPasswordException {
        return ResponseEntity.ok(userService.getJwtFromNameAndPassword(request));
    }
}
