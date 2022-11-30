package com.example.inside_test_task.service;

import com.example.inside_test_task.exception.InvalidTokenException;
import com.example.inside_test_task.exception.UserNotFoundException;

import java.util.HashMap;

public interface MessageService {
    HashMap<String, Object> getMessageFromClient(String username, String message, String header)
            throws UserNotFoundException, InvalidTokenException;
}
