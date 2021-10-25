package com.unitable.unitableprojectupc.handler;

import com.unitable.unitableprojectupc.exception.ChatNotFoundException;
import com.unitable.unitableprojectupc.exception.CursoNotFoundException;
import com.unitable.unitableprojectupc.exception.GrupoBadRequestException;
import com.unitable.unitableprojectupc.exception.IncorrectUsuarioRequestException;
import com.unitable.unitableprojectupc.exception.MessageBadRequestException;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception exception, WebRequest request) {
        UsuarioServiceExceptionResponse response = new UsuarioServiceExceptionResponse(
                exception.getMessage(), request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now()
        );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(IncorrectUsuarioRequestException.class)
    public ResponseEntity<Object> handleIncorrectRequest(Exception exception, WebRequest request) {
        UsuarioServiceExceptionResponse response = new UsuarioServiceExceptionResponse(
                exception.getMessage(), request.getDescription(false),
                HttpStatus.BAD_REQUEST, LocalDateTime.now()
        );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(GrupoBadRequestException.class)
    public ResponseEntity<Object> handleGrupoIncorrectRequest(Exception exception, WebRequest request) {
        UsuarioServiceExceptionResponse response = new UsuarioServiceExceptionResponse(
                exception.getMessage(), request.getDescription(false),
                HttpStatus.BAD_REQUEST, LocalDateTime.now()
        );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(MessageBadRequestException.class)
    public ResponseEntity<Object> handleMessageIncorrectRequest(Exception exception, WebRequest request) {
        UsuarioServiceExceptionResponse response = new UsuarioServiceExceptionResponse(
                exception.getMessage(), request.getDescription(false),
                HttpStatus.BAD_REQUEST, LocalDateTime.now()
        );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(CursoNotFoundException.class)
    public ResponseEntity<Object> handleCursoNotFound(Exception exception, WebRequest request) {
        UsuarioServiceExceptionResponse response = new UsuarioServiceExceptionResponse(
                exception.getMessage(), request.getDescription(false),
                HttpStatus.NOT_FOUND, LocalDateTime.now()
        );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<Object> handleChatNotFound(Exception exception, WebRequest request) {
        UsuarioServiceExceptionResponse response = new UsuarioServiceExceptionResponse(
                exception.getMessage(), request.getDescription(false),
                HttpStatus.NOT_FOUND, LocalDateTime.now()
        );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(Exception exception, WebRequest request) {
        UsuarioServiceExceptionResponse response = new UsuarioServiceExceptionResponse(
                exception.getMessage(), request.getDescription(false),
                HttpStatus.NOT_FOUND, LocalDateTime.now()
        );
        return new ResponseEntity<>(response, response.getStatus());
    }
}
