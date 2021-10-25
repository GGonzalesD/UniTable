package com.unitable.unitableprojectupc.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class MensajeResponse {
    private Long id;
    private String mensaje;
    private Time hora_mensaje;
    private Long usuario_id;
    private Long chat_id;
}
