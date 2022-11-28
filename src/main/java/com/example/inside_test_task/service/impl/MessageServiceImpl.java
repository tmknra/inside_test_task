package com.example.inside_test_task.service.impl;

import com.example.inside_test_task.dto.in.MessageRequest;
import com.example.inside_test_task.entity.MyMessage;
import com.example.inside_test_task.mapper.MessageMapper;
import com.example.inside_test_task.repository.MessageRepository;
import com.example.inside_test_task.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public Object getMessageFromClient(MessageRequest request) {
        Object res = null;
        if (true /*здесь проверяется токен*/){
            if (request.getMessage().startsWith("history")){
                Page<MyMessage> lastMessages =
                        messageRepository.findLastMessages(Integer.valueOf(request.getMessage().split(" ")[1]));
                res = lastMessages;
            } else {
                messageRepository.save(messageMapper.messageRequestToMyMessage(request));
                res = "success";
            }
        }
        return res;
    }
}
