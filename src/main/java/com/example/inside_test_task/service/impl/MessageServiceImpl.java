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

    /**
      Получает сообщения от клиента и записывает их в базу.
     */
    @Override
    @Transactional
    public HashMap<String, Object> getMessageFromClient(String username, String message, String header) throws UserNotFoundException, InvalidTokenException {
        HashMap<String, Object> response = new HashMap<>();
        /**
         Сначала проверяем корректность введеного имени пользователя. Если такого пользователя нет,
         получаем Exception.
         */
        Optional<MyUser> byName = userRepository.findByName(username);
        if (byName.isEmpty()) {
            throw new UserNotFoundException("Provided user not found");
        }
        /**
         Проверяем корректность JWT токена, полученного из хедера Authorization. Если токен некорректен,
         получаем Exception.
         */
        String token = header.split("Bearer_")[1];
        if (!JWT.decode(token).getSubject().equals(username)) {
            throw new InvalidTokenException("Invalid token");
        }
        /**
          Уникальный случай, когда сообщение пользователя несет в себе не зависимую от регистра команду "history число".
          Отдает из базы указанное число последних сообщений.
         */
        if (message.matches("(?i)history \\d")) {
            response.put("message", messageRepository.findLastMessages(message.split(" ")[1]));
        }
        /**
          Базовый случай обработки сообщения - записывает его в базу.
         */
        else {
            MyMessage myMessage = new MyMessage(byName.get(), message);
            messageRepository.save(myMessage);
            response.put("message", "Message successfully added to database.");
        }
        /**
         На выход отдаем HashMap, что достаточно в данном случае, с одним элементом вида "ключ: название поля,
         значение: сообщение". На выходе HashMap преобразуется в JSON.
         Для более специфических ситуаций можно создать DTO для ответа фронту с необходимыми полями.
         */
        return response;
    }
}
