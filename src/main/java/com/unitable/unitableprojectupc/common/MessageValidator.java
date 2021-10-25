package com.unitable.unitableprojectupc.common;

import com.unitable.unitableprojectupc.dto.MensajeRequest;
import com.unitable.unitableprojectupc.exception.MessageBadRequestException;

public class MessageValidator {
	public static boolean validateUser(MensajeRequest msjRqst) {
        
		if(  msjRqst.getMensaje()==null || msjRqst.getUsuario_id()==null || msjRqst.getChat_id()==null || msjRqst.getMensaje().isBlank() ){
			throw new MessageBadRequestException("Completar todos los datos en blanco.");
		}
		
        return true;
    }
}
