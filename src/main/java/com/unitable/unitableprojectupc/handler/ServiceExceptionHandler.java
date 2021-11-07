package com.unitable.unitableprojectupc.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;

import com.unitable.unitableprojectupc.exception.BadResourceRequestException;
import com.unitable.unitableprojectupc.exception.GeneralServiceException;
import com.unitable.unitableprojectupc.exception.ResourceNotFoundException;
import com.unitable.unitableprojectupc.utils.WrapperResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.stream.Collectors;


@ControllerAdvice
@RestController
@Order(-1)
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getBindingResult().getAllErrors().stream().map(e -> {
            return e.getDefaultMessage().toString().concat(", ");
        }).collect(Collectors.joining());
        WrapperResponse<?> response = new WrapperResponse<>(false, message, null);
        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WrapperResponse<?>> all(Exception e, WebRequest request){
        WrapperResponse<?> response = new WrapperResponse<>(false, "Internal Server Error: " + e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadResourceRequestException.class)
    public ResponseEntity<WrapperResponse<?> > validateServiceException(BadResourceRequestException e, WebRequest request){
        WrapperResponse<?> response = new WrapperResponse<>(false, e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<WrapperResponse<?> > noDataFoundException(ResourceNotFoundException e, WebRequest request){
        WrapperResponse<?> response = new WrapperResponse<>(false, e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GeneralServiceException.class)
    public ResponseEntity<WrapperResponse<?> > generalServiceException(GeneralServiceException e, WebRequest request){
        WrapperResponse<?> response = new WrapperResponse<>(false, "Internal Server Error: " + e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
