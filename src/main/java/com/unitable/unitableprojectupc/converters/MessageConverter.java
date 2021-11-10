package com.unitable.unitableprojectupc.converters;


import com.unitable.unitableprojectupc.dto.MensajeRequest;
import com.unitable.unitableprojectupc.dto.MensajeResponse;
import com.unitable.unitableprojectupc.entities.Mensaje;

import org.springframework.stereotype.Component;

@Component
public class MessageConverter extends  AbstractConverter<Mensaje, MensajeRequest, MensajeResponse> {
    @Override
    public MensajeResponse fromEntity(Mensaje message) {
        if(message == null) return null;

        MensajeResponse mensajeResponse = MensajeResponse.builder()
				.id(message.getId())
				.mensaje(message.getMensaje())
				.hora_mensaje(message.getHora_mensaje())
				.usuario_id( message.getUsuario().getId() )
                .build();

        return mensajeResponse;
    }

    @Override
    public Mensaje fromRequest(MensajeRequest dto) {
		if(dto == null) return null;
        return Mensaje.builder()
				.mensaje( dto.getMensaje() )
				.build();
    }
}
