package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.dto.ChatRequest;
import com.unitable.unitableprojectupc.entities.Chat;
import com.unitable.unitableprojectupc.entities.Mensaje;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.exception.BadResourceRequestException;
import com.unitable.unitableprojectupc.exception.ResourceNotFoundException;
import com.unitable.unitableprojectupc.repository.ChatRepository;
import com.unitable.unitableprojectupc.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
	@Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MensajeService mensajeService;

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Chat createChat(ChatRequest chatRequest) {
        Chat newChat = initChat(chatRequest); //
        return chatRepository.save(newChat);
    }

	@Transactional(readOnly = true)
    public List<Chat> findAllChats() {
        List<Chat> chats = chatRepository.findAll();
        return chats;
    }

    @Transactional(readOnly = true)
    public Chat findChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
			.orElseThrow( () -> ResourceNotFoundException.byIndex("Chat", chatId));
        
        Usuario usuario = UserPrincipal.getCurrentUser();

        if(usuario.getGrupos().stream().filter(g -> g.getChat().getId() == chat.getId()).findFirst().isPresent()){
            return chat;
        }else{
            throw new BadResourceRequestException("Usuario '" + usuario.getId() + "' No está en el grupo!");
        }
    }

    @Transactional
    public void deleteChat(Chat chat){

        for (Mensaje mensaje : chat.getMensajes().toArray(new Mensaje[0])) {
            chat.getMensajes().remove(mensaje);
            mensajeService.deleteMensage(mensaje);
        }

        chatRepository.delete(chat);
    }

	private Chat initChat(ChatRequest chatRequest) {
        Chat chat = new Chat();
        
		chat.setCant_mensajes(Integer.valueOf(0));
		chat.setDetalles(chatRequest.getDetalles());
        chat.setMensajes(new ArrayList<Mensaje>());
        
        return chat;
    }

}
