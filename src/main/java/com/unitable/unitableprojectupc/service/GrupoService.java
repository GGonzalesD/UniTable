package com.unitable.unitableprojectupc.service;


import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.common.GrupoValidator;
import com.unitable.unitableprojectupc.dto.GrupoRequest;
import com.unitable.unitableprojectupc.dto.GrupoResponse;
import com.unitable.unitableprojectupc.entities.Chat;
import com.unitable.unitableprojectupc.entities.Curso;
import com.unitable.unitableprojectupc.entities.Grupo;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.exception.ResourceNotFoundException;
import com.unitable.unitableprojectupc.repository.ChatRepository;
import com.unitable.unitableprojectupc.repository.GrupoRepository;
import com.unitable.unitableprojectupc.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private UsuarioService usuarioService;
    @Autowired
    private CursoService cursoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatService chatService;
    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @Transactional(readOnly = true)
    public Page<GrupoResponse> findAllGroupsPage(Pageable pageable) {
            Page<GrupoResponse> grupos = grupoRepository.findAll(pageable).map(
                grupo->entityDtoConverter.convertEntityToDtoGrupo(grupo));
        return grupos;
    }

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
			.orElseThrow( () -> ResourceNotFoundException.byIndex("Usuario", userId) );
        cursoService.findCursoById(cursoId);

        List<Grupo> grupos = grupoRepository.findByUsuarioAndCurso(userId, cursoId);

        return grupos;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation =Propagation.REQUIRED)
    public Grupo createGrupo(GrupoRequest grupoRequest) {
        Grupo newGrupo = initGrupo(grupoRequest);
        return grupoRepository.save(newGrupo);
    }

    @Transactional
    public Grupo updateGrupoById(Long grupoId, GrupoRequest grupoRequest) {
        GrupoValidator.validateGrupo(grupoRequest);
        Grupo grupo = grupoRepository.findById(grupoId).
                orElseThrow(() -> ResourceNotFoundException.byIndex("Grupo", grupoId) );
        
        Curso curso = cursoService.findCursoById(grupoRequest.getCurso_id());

        grupo.setNombre(grupoRequest.getNombre());
        grupo.setTema(grupoRequest.getTema());
        grupo.setDescripcion(grupoRequest.getDescripcion());
        grupo.setCurso(curso);

        return grupoRepository.save(grupo);
    }

    @Transactional
    public void deleteGrupo(Long grupoId){
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(()-> ResourceNotFoundException.byIndex("Grupo", grupoId) );
        
        for (Usuario usuario : grupo.getUsuarios().toArray(new Usuario[0])) {
            grupo.getUsuarios().remove(usuario);
            usuario.getGrupos().remove(grupo);
        }
        
        chatService.deleteChat(grupo.getChat());
        grupoRepository.delete(grupo);
    }

    private Grupo initGrupo(GrupoRequest grupoRequest) {
        GrupoValidator.validateGrupo(grupoRequest);
        Usuario usuario = usuarioService.findUsuarioById(grupoRequest.getUsuario_id());
        Curso curso = cursoService.findCursoById(grupoRequest.getCurso_id());


        Chat chat = Chat.builder()
            .cant_mensajes(0)
            .detalles("Chat del grupo '" + grupoRequest.getNombre() + "'")
            .build();
        chat = chatRepository.save(chat);


        Grupo grupo = new Grupo();
        grupo.setNombre(grupoRequest.getNombre());
        grupo.setDescripcion(grupoRequest.getDescripcion());
        grupo.setFecha_creacion(Date.valueOf(LocalDate.now()));
        grupo.setFecha_fin(null);
        grupo.setTema(grupoRequest.getTema());
        grupo.setCurso(curso);
        grupo.setChat(chat);
        grupo.setUsuarios(new ArrayList<Usuario>());
        usuario.getGrupos().add(grupo);

        return grupo;
    }
}