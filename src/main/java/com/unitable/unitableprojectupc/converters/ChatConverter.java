package com.unitable.unitableprojectupc.converters;

import com.unitable.unitableprojectupc.dto.ChatRequest;
import com.unitable.unitableprojectupc.dto.ChatResponse;
import com.unitable.unitableprojectupc.entities.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatConverter extends  AbstractConverter<Chat, ChatRequest, ChatResponse> {

	@Autowired
	private MessageConverter messageConverter;

	@Override
    public ChatResponse fromEntity(Chat chat) {
        if(chat == null) return null;
        return ChatResponse.builder()
				.id(chat.getId())
				.cant_mensajes( chat.getCant_mensajes() )
				.detalles( chat.getDetalles() )
				.grupo_id( chat.getGrupo().getId() )
				.mensajes( messageConverter.fromEntity(chat.getMensajes()) )
                .build();
    }

    @Override
    public Chat fromRequest(ChatRequest dto) {
		if(dto == null) return null;
        return Chat.builder()
				.cant_mensajes( 0 )
				.detalles( dto.getDetalles() )
				.build();
    }
}
