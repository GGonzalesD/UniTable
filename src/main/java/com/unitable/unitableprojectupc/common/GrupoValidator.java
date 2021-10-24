package com.unitable.unitableprojectupc.common;

import com.unitable.unitableprojectupc.dto.GrupoRequest;
import com.unitable.unitableprojectupc.exception.GrupoBadRequestException;

public class GrupoValidator {
	public static boolean validateGrupo(GrupoRequest grupoRequest){
		if(grupoRequest.getCurso_id() == null){
			throw new GrupoBadRequestException("Es necesario pasar un ID de curso al grupo");
		}
		
		return true;
	}
}
