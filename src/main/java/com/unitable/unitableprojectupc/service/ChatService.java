package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.dto.ChatRequest;
import com.unitable.unitableprojectupc.entities.Chat;
import com.unitable.unitableprojectupc.repository.ChatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatService {
	@Autowired
    private ChatRepository chatRepository;

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Chat createChat(ChatRequest chatRequest) {
        Chat newChat = iniChat(chatRequest); //
        return chatRepository.save(newChat);
    }

	@Transactional(readOnly = true)
    public List<Chat> findAllChats() {
        List<Chat> chats = chatRepository.findAll();
        return chats;
    }

	private Chat iniChat(ChatRequest chatRequest) {
        Chat chat = new Chat();
		chat.setCant_mensajes(chat.getCant_mensajes());
		chat.setDetalles(chat.getDetalles());
        return chat;
    }

}
