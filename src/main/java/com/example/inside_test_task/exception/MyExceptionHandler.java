package com.example.inside_test_task.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Обработка основных исключений, получаемых в процессе работы с приложением. При возникновении ошибки
     * фронту пробрасывается сообщение с текстом ошибки.
     */
    @ExceptionHandler(value
            = {InvalidPasswordException.class, UserNotFoundException.class, InvalidTokenException.class,
            JWTDecodeException.class})
    protected ResponseEntity<Object> handleConflict(Exception ex){
        HashMap<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());

        if (ex.getClass()==InvalidPasswordException.class || ex.getClass() == InvalidTokenException.class ||
                ex.getClass() == JWTDecodeException.class){
            return ResponseEntity.status(UNAUTHORIZED).body(map);
        }
        if (ex.getClass()==UserNotFoundException.class){
            return ResponseEntity.status(NOT_FOUND).body(map);
        }
        return ResponseEntity.status(BAD_REQUEST).body(map);
    }
}
