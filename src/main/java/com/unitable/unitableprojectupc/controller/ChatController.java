package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.dto.ChatRequest;
import com.unitable.unitableprojectupc.dto.ChatResponse;
import com.unitable.unitableprojectupc.entities.Chat;
import com.unitable.unitableprojectupc.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping("/chat") 
public class ChatController {
	@Autowired
    private ChatService chatService;

    @Autowired
    private EntityDtoConverter entityDtoConverter;

	@PostMapping
    public ResponseEntity<ChatResponse> createChat(@RequestBody ChatRequest chatRequest) {
        Chat chat = chatService.createChat(chatRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoChat(chat), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<ChatResponse>> findAll() {
        List<Chat> chats = chatService.findAllChats();
        return new ResponseEntity<List<ChatResponse>>(
                entityDtoConverter.convertEntityToDtoChat(chats),
                HttpStatus.OK);
    }
}
