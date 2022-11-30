package com.example.inside_test_task.service.impl;

import com.auth0.jwt.JWT;
import com.example.inside_test_task.entity.MyMessage;
import com.example.inside_test_task.entity.MyUser;
import com.example.inside_test_task.exception.InvalidTokenException;
import com.example.inside_test_task.exception.UserNotFoundException;
import com.example.inside_test_task.repository.MessageRepository;
import com.example.inside_test_task.repository.UserRepository;
import com.example.inside_test_task.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;


@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public HashMap<String, Object> getMessageFromClient(String username, String message, String header) throws UserNotFoundException, InvalidTokenException {
        HashMap<String, Object> response = new HashMap<>();
        Optional<MyUser> byName = userRepository.findByName(username);
        if (byName.isEmpty()) {
            throw new UserNotFoundException("Provided user not found");
        }
        String token = header.split("Bearer_")[1];
        if (!JWT.decode(token).getSubject().equals(username)) {
            throw new InvalidTokenException("Invalid token");
        }
        if (message.matches("(?i)history \\d")) {
            response.put("message", messageRepository.findLastMessages(message.split(" ")[1]));
        } else {
            MyMessage myMessage = new MyMessage(byName.get(), message);
            messageRepository.save(myMessage);
            response.put("message", "Message successfully added to database.");
        }
        return response;
    }
}
