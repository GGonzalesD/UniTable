package com.unitable.unitableprojectupc.common;

import com.unitable.unitableprojectupc.dto.UsuarioRequest;
import com.unitable.unitableprojectupc.exception.IncorrectUsuarioRequestException;

public class UsuarioValidator {
    public static boolean validateUser(UsuarioRequest usuario) {
        if(usuario.getNombres().isBlank() || usuario.getApellidos().isBlank() ||
        usuario.getCorreo().isBlank() || usuario.getPassword().isBlank() || usuario.getCarrera().isBlank()) {
            throw new IncorrectUsuarioRequestException("Completar todos los datos en blanco.");
        }
        return true;
    }
}
