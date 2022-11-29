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

    @Override
    public HashMap<String, String> getJwtFromNameAndPassword(JWTRequest request) throws UserNotFoundException, InvalidPasswordException {
        HashMap<String, String> result = new HashMap<>();
        Optional<MyUser> byName = userRepository.findByName(request.getName());
        if (byName.isEmpty()) {
            throw new UserNotFoundException("Provided user not found");
        }
        if (!byName.get().getPassword().equals(request.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String token = JWT.create()
                .withSubject(request.getName())
                .sign(algorithm);
        result.put("token", token);

        return result;
    }
}
