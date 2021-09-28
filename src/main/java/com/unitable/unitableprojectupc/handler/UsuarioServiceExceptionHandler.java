package com.unitable.unitableprojectupc.handler;

import com.unitable.unitableprojectupc.exception.UserNotFoundException;
import com.unitable.unitableprojectupc.exception.UsuarioServiceExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class UsuarioServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(Exception exception, WebRequest request) {
        UsuarioServiceExceptionResponse response = new UsuarioServiceExceptionResponse(
                exception.getMessage(), request.getDescription(false),
                HttpStatus.BAD_REQUEST, LocalDateTime.now()
        );
        return new ResponseEntity<>(response, response.getStatus());
    }
}
