package com.unitable.unitableprojectupc.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CursoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
}
