package com.unitable.unitableprojectupc.dto;

import lombok.Getter;

@Getter
public class UsuarioRequest {
    private String nombres;
    private String apellidos;
    private String correo;
    private String password;
    private String carrera;
}
