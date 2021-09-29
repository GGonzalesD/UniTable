package com.unitable.unitableprojectupc.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ActividadResponse {
    private Long id;
    private String nombre;
    private String detalles;
    private Date fecha_ini;
    private Date fecha_fin;
    private Integer duracion_min;
    private Boolean activa;
}
