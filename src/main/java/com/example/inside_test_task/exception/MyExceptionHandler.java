package com.example.inside_test_task.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = {InvalidPasswordException.class, UserNotFoundException.class, InvalidTokenException.class})
    protected ResponseEntity<Object> handleConflict(Exception ex){
        HashMap<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        if (ex.getClass()==InvalidPasswordException.class || ex.getClass() == InvalidTokenException.class){
            return ResponseEntity.status(UNAUTHORIZED).body(map);
        }
        if (ex.getClass()==UserNotFoundException.class){
            return ResponseEntity.status(NOT_FOUND).body(map);
        }
        return ResponseEntity.status(BAD_REQUEST).body(map);
    }
}
