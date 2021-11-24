package com.unitable.unitableprojectupc.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Builder
public class MensajeResponse {
    private Long id;
    private String mensaje;
    private Time hora_mensaje;
    private Long usuario_id;
    private String usuario_name;
    private Long chat_id;
}
