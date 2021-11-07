package com.unitable.unitableprojectupc.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class GrupoRequest {

    @NotNull
    @NotBlank
    private String nombre;
    private String tema;
    private String descripcion;
    private String curso_n;
    private String curso_d;
}
