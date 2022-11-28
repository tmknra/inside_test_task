package com.example.inside_test_task.service;

import com.example.inside_test_task.dto.in.JWTRequest;

import java.util.HashMap;

public interface UserService {
    HashMap<String, String> getJwtFromNameAndPassword(JWTRequest request);
}
