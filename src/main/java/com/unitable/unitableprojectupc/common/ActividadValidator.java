package com.unitable.unitableprojectupc.common;

import com.unitable.unitableprojectupc.dto.ActividadRequest;
import com.unitable.unitableprojectupc.exception.BadResourceRequestException;

public class ActividadValidator {
    public static boolean validateActividad(ActividadRequest actividad) {
        if(actividad.getNombre().isBlank() || actividad.getDetalles().isBlank()) {
            throw new BadResourceRequestException("Completar todos los datos en blanco.");
        }
        return true;
    }
}
