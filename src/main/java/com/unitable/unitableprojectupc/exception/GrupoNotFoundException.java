package com.unitable.unitableprojectupc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GrupoNotFoundException extends RuntimeException{
	public GrupoNotFoundException(String message){
		super(message);
	}
}

