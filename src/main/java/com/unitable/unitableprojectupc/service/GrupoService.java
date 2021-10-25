package com.unitable.unitableprojectupc.service;


import com.unitable.unitableprojectupc.common.GrupoValidator;
import com.unitable.unitableprojectupc.dto.GrupoRequest;
import com.unitable.unitableprojectupc.entities.Chat;
import com.unitable.unitableprojectupc.entities.Curso;
import com.unitable.unitableprojectupc.entities.Grupo;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.exception.UserNotFoundException;
import com.unitable.unitableprojectupc.repository.GrupoRepository;
import com.unitable.unitableprojectupc.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class GrupoService {
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private CursoService cursoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ChatService chatService;

    @Transactional(readOnly = true)
    public List<Grupo> findAllGroups() {
        List<Grupo> grupos = grupoRepository.findAll();
        return grupos;
    }

    @Transactional(readOnly = true)
    public Grupo findGrupoById(Long id) {
        Optional<Grupo> grupo = Optional.ofNullable(grupoRepository.findGrupoById(id));
        return grupo.orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Grupo> findgrupoByUsuarioAndCurso(Long userId, Long cursoId) {
        
        usuarioRepository.findById(userId)
			.orElseThrow( () -> new UserNotFoundException("User '"+userId+"' Not found"));
        cursoService.findCursoById(cursoId);

        List<Grupo> grupos = grupoRepository.findByUsuarioAndCurso(userId, cursoId);

        return grupos;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation =Propagation.REQUIRED)
    public Grupo createGrupo(GrupoRequest grupoRequest) {
        Grupo newGrupo = initGrupo(grupoRequest);
        return grupoRepository.save(newGrupo);
    }

    private Grupo initGrupo(GrupoRequest grupoRequest) {
        GrupoValidator.validateGrupo(grupoRequest);
        Curso curso = cursoService.findCursoById(grupoRequest.getCurso_id());
        Chat chat = chatService.findChatById(grupoRequest.getChat_id());

        Grupo grupo = new Grupo();
        grupo.setNombre(grupoRequest.getNombre());
        grupo.setDescripcion(grupoRequest.getDescripcion());
        grupo.setFecha_creacion(Date.valueOf(LocalDate.now()));
        grupo.setFecha_fin(null);
        grupo.setTema(grupoRequest.getTema());
        grupo.setCurso(curso);
        grupo.setChat(chat);
        grupo.setUsuarios(new ArrayList<Usuario>());

        return grupo;
    }
}