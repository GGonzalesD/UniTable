package com.unitable.unitableprojectupc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException byIndex(String resourse, Long resourceId){
        return new ResourceNotFoundException(resourse + " '" + resourceId + "' Not found!");
    }
}

