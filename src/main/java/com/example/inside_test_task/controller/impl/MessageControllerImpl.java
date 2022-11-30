package com.example.inside_test_task.controller.impl;


import com.example.inside_test_task.controller.MessageController;
import com.example.inside_test_task.dto.in.MessageRequest;
import com.example.inside_test_task.exception.InvalidTokenException;
import com.example.inside_test_task.exception.UserNotFoundException;
import com.example.inside_test_task.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageControllerImpl implements MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageControllerImpl(MessageService messageService) {
        this.messageService = messageService;
    }


    @Override
    public ResponseEntity<?> getMessageFromClient(MessageRequest request, String header)
            throws UserNotFoundException, InvalidTokenException {
        return ResponseEntity.ok(messageService.getMessageFromClient(request.getName(), request.getMessage(), header));
    }

}
