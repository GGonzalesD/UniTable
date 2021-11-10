package com.unitable.unitableprojectupc.common;

import com.unitable.unitableprojectupc.dto.MensajeRequest;
import com.unitable.unitableprojectupc.exception.BadResourceRequestException;

public class MessageValidator {
	public static boolean validateUser(MensajeRequest msjRqst) {
        
		if(  msjRqst.getMensaje()==null || msjRqst.getChat_id()==null || msjRqst.getMensaje().isBlank() ){
			throw new BadResourceRequestException("Completar todos los datos en blanco.");
		}
		
        return true;
    }
}
