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

    public GrupoResponse convertEntityToDtoGrupo(Grupo grupo){
        GrupoResponse grupoResponse = modelMapper.map(grupo, GrupoResponse.class);
        grupoResponse.setChat( convertEntityToDtoChat(grupo.getChat()) );
        return grupoResponse;
    }

    public ActividadResponse convertEntityToDtoActividad(Actividad actividad) {
        return modelMapper.map(actividad, ActividadResponse.class);
    }

    public ChatResponse convertEntityToDtoChat(Chat chat){
        ChatResponse chatResponse = modelMapper.map(chat, ChatResponse.class);
        if(chat.getGrupo()!=null)
            chatResponse.setGrupo_id(chat.getGrupo().getId());
        return chatResponse;
    }

    public CursoResponse convertEntityToDtoCurso(Curso curso) {
        return modelMapper.map(curso, CursoResponse.class);
    }

    public MensajeResponse convertEntityToDtoMensaje(Mensaje mensaje) {
        //return modelMapper.map(mensaje, MensajeResponse.class);
        MensajeResponse messageResponse = modelMapper.map(mensaje, MensajeResponse.class);
        if(mensaje.getChat()!=null)
            messageResponse.setChat_id( mensaje.getChat().getId() );
        if(mensaje.getUsuario()!=null)
            messageResponse.setUsuario_id( mensaje.getUsuario().getId() );
        return messageResponse;
    }

    public List<UsuarioResponse> convertEntityToDtoUser(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::convertEntityToDtoUser)
                .collect(Collectors.toList());
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

    public List<ActividadResponse> convertEntityToDtoActividad(List<Actividad> actividades) {
        return actividades.stream()
                .map(this::convertEntityToDtoActividad)
                .collect(Collectors.toList());
    }

    public List<ChatResponse> convertEntityToDtoChat(List<Chat> chats){
        return chats.stream()
                .map(this::convertEntityToDtoChat)
                .collect(Collectors.toList());
    }

    public List<CursoResponse> convertEntityToDtoCurso(List<Curso> cursos){
        return cursos.stream()
                .map(this::convertEntityToDtoCurso)
                .collect(Collectors.toList());
    }

    public List<MensajeResponse> convertEntityToDtoMensaje(List<Mensaje> mensajes) {
        return mensajes.stream()
                .map(this::convertEntityToDtoMensaje)
                .collect(Collectors.toList());
    }
}
