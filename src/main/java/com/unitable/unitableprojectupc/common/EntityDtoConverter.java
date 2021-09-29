package com.unitable.unitableprojectupc.common;

import com.unitable.unitableprojectupc.dto.*;
import com.unitable.unitableprojectupc.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioResponse convertEntityToDtoUser(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    public RecompensaResponse convertEntityToDtoRecompensa(Recompensa recompensa) {
        return modelMapper.map(recompensa, RecompensaResponse.class);
    }

    public List<UsuarioResponse> convertEntityToDtoUser(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::convertEntityToDtoUser)
                .collect(Collectors.toList());
    }


    public GrupoResponse convertEntityToDtoGrupo(Grupo grupo){
        return modelMapper.map(grupo, GrupoResponse.class);

    }

    public List<GrupoResponse> convertEntityToDtoGrupo(List<Grupo> grupos) {
        return grupos.stream()
                .map(this::convertEntityToDtoGrupo)
                .collect(Collectors.toList());
    }



    public List<RecompensaResponse> convertEntityToDtoRecompensa(List<Recompensa> recompensas) {
        return recompensas.stream()
                .map(this::convertEntityToDtoRecompensa)
                .collect(Collectors.toList());
    }

    public ActividadResponse convertEntityToDtoActividad(Actividad actividad) {
        return modelMapper.map(actividad, ActividadResponse.class);
    }

    public List<ActividadResponse> convertEntityToDtoActividad(List<Actividad> actividades) {
        return actividades.stream()
                .map(this::convertEntityToDtoActividad)
                .collect(Collectors.toList());
    }

    public ChatResponse convertEntityToDtoChat(Chat chat){
        return modelMapper.map(chat, ChatResponse.class);
    }

    public List<ChatResponse> convertEntityToDtoChat(List<Chat> chats){
        return chats.stream()
                .map(this::convertEntityToDtoChat)
                .collect(Collectors.toList());
    }
}
