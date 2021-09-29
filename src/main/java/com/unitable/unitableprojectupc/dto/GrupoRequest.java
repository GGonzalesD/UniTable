package com.unitable.unitableprojectupc.dto;


import lombok.Getter;

import java.util.Date;

@Getter

public class GrupoRequest {

    private String nombre;
    private String tema;
    private String descripcion;
    private Date fecha_creacion;
    private Date fecha_fin;


}
