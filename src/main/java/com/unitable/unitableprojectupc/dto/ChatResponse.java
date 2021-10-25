package com.unitable.unitableprojectupc.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResponse {
    private Long id;
    private Integer cant_mensajes;
    private String detalles;
    private Long grupo_id;
}
