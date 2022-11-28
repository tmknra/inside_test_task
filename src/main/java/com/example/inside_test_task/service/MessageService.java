package com.example.inside_test_task.service;

import com.example.inside_test_task.dto.in.MessageRequest;

public interface MessageService {


    Object getMessageFromClient(MessageRequest request);
}
