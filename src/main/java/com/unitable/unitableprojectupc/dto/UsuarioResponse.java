package com.unitable.unitableprojectupc.dto;

import com.unitable.unitableprojectupc.common.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse {
    private String nombres;
    private String apellidos;
    private String correo;
    private String carrera;
    private Integer num_act_completas;
    private Integer num_monedas;
    private UserType tipo_usuario;
}
