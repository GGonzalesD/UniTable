package com.unitable.unitableprojectupc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GrupoBadRequestException extends RuntimeException{
	public GrupoBadRequestException(String message){
		super(message);
	}
}


