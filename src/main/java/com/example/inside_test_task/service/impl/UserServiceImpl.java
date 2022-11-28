package com.example.inside_test_task.service.impl;

import com.example.inside_test_task.dto.in.JWTRequest;
import com.example.inside_test_task.entity.MyUser;
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
    public HashMap<String, String> getJwtFromNameAndPassword(JWTRequest request) {
        Optional<MyUser> byName = userRepository.findByName(request.getName());
        if (!byName.isEmpty() && byName.get().getPassword().equals(request.getPassword()))
        {
            //сгенерировать токен
        }
        HashMap<String, String> result = new HashMap<>();
        result.put("token", "здесь будет токен");
        return result;
    }
}
