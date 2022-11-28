package com.example.inside_test_task.service.impl;

import com.example.inside_test_task.entity.MyMessage;
import com.example.inside_test_task.entity.MyUser;
import com.example.inside_test_task.mapper.MessageMapper;
import com.example.inside_test_task.repository.MessageRepository;
import com.example.inside_test_task.repository.UserRepository;
import com.example.inside_test_task.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;


@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository,
                              MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<?> getMessageFromClient(String username, String message) {
        if (true /*здесь проверяется токен*/) {
            if (message.startsWith("history")) {
                return ResponseEntity.ok(messageRepository.findLastMessages(message.split(" ")[1]));
            } else {
                MyUser myUser = userRepository.findByName(username).get();
                MyMessage myMessage = new MyMessage(myUser, message);
                messageRepository.save(myMessage);
                HashMap<String, String> response = new HashMap<>();
                response.put("message", "Message successfully added to database.");
                ResponseEntity.ok().body(response);
            }
        }
        return null;
    }
}
