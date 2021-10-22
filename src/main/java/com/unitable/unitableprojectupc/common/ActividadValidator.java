package com.unitable.unitableprojectupc.common;

import com.unitable.unitableprojectupc.dto.ActividadRequest;
import com.unitable.unitableprojectupc.exception.IncorrectActividadRequestException;

public class ActividadValidator {
    public static boolean validateActividad(ActividadRequest actividad) {
        if(actividad.getNombre().isBlank() || actividad.getDetalles().isBlank()) {
            throw new IncorrectActividadRequestException("Completar todos los datos en blanco.");
        }
        return true;
    }
}
