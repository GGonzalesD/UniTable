package com.unitable.unitableprojectupc.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.unitable.unitableprojectupc.common.UserType;

import lombok.Getter;

@Getter
public class UsuarioRequest {
    @NotBlank
    private String nombres;
    @NotBlank
    private String apellidos;
    @NotBlank
    private String correo;
    @NotBlank
    private String password;
    @NotBlank
    private String carrera;

    @NotNull
    private UserType tipo_usuario;
}
