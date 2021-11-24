package com.unitable.unitableprojectupc.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.unitable.unitableprojectupc.entities.Curso;

@Getter
@Setter
@Builder
public class GrupoResponse {

    private Long id;
    private String nombre;
    private String tema;
    private String descripcion;
    private Date fecha_creacion;
    private Date fecha_fin;
    private ChatResponse chat;
    private Curso curso;
    private Boolean usuario_grupo;

}
