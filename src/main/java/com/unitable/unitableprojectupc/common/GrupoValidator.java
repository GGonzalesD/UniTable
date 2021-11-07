package com.unitable.unitableprojectupc.common;

import com.unitable.unitableprojectupc.dto.GrupoRequest;
import com.unitable.unitableprojectupc.exception.BadResourceRequestException;

public class GrupoValidator {
	public static boolean validateGrupo(GrupoRequest grupoRequest){
		if(grupoRequest.getNombre() == null){
			throw new BadResourceRequestException("Es necesario pasar el ID del curso y del chat al grupo");
		}
		
		return true;
	}
}
