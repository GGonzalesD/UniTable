package com.unitable.unitableprojectupc.dto;


import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChatResponse {
    private Long id;
    private Integer cant_mensajes;
    private String detalles;
    private Long grupo_id;
    private List<MensajeResponse> mensajes;
}
