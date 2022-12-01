package com.example.inside_test_task.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.inside_test_task.dto.in.JWTRequest;
import com.example.inside_test_task.entity.MyUser;
import com.example.inside_test_task.exception.InvalidPasswordException;
import com.example.inside_test_task.exception.UserNotFoundException;
import com.example.inside_test_task.repository.UserRepository;
import com.example.inside_test_task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Проверяет корректность пароля, введеного юзером. Если пароль корректен, генерирует JWT токен.
     */
    @Override
    public HashMap<String, String> getJwtFromNameAndPassword(JWTRequest request)
            throws UserNotFoundException, InvalidPasswordException {
        HashMap<String, String> result = new HashMap<>();
        /**
         * Сначала проверяем корректность введеного имени пользователя. Если такого пользователя нет,
         * получаем Exception.
         */
        Optional<MyUser> byName = userRepository.findByName(request.getName());
        if (byName.isEmpty()) {
            throw new UserNotFoundException("Provided user not found");
        }
        /**
         * Если пользователь найден, проверяем корректность введенного пароля.
         */
        if (!byName.get().getPassword().equals(request.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        /**
         * Если пароль корректен, генерируем JWT токен. В токен записываем name пользователя. Срок действия не прописываем,
         * т.к. это разрешено условием задачи.
         */
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String token = JWT.create()
                .withSubject(request.getName())
                .sign(algorithm);
        result.put("token", token);
        /**
         * На выход отдаем HashMap, что достаточно в данном случае, с одним элементом вида "ключ: название поля,
         * значение: сообщение". На выходе HashMap преобразуется в JSON.
         * Для более специфических ситуаций можно создать DTO для ответа фронту с необходимыми полями.
         */
        return result;
    }
}
