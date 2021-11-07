package com.unitable.unitableprojectupc.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CursoRequest {
    private String nombre;
    private String descripcion;
}
