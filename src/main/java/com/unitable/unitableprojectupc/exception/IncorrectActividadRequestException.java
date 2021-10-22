package com.unitable.unitableprojectupc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IncorrectActividadRequestException extends RuntimeException{
    public IncorrectActividadRequestException(String message) {
        super(message);
    }
}
