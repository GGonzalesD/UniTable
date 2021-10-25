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
    @NotNull
    private Long curso_id;
    @NotNull
    private Long chat_id;

}
