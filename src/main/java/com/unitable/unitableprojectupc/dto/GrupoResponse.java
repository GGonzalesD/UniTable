package com.unitable.unitableprojectupc.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class GrupoResponse {

    private Long id;
    private String nombre;
    private String tema;
    private String descripcion;
    private Date fecha_creacion;
    private Date fecha_fin;


}
