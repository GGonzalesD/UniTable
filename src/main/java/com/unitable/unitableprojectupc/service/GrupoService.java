package com.unitable.unitableprojectupc.service;


import com.unitable.unitableprojectupc.common.GrupoValidator;
import com.unitable.unitableprojectupc.dto.GrupoRequest;
import com.unitable.unitableprojectupc.entities.Curso;
import com.unitable.unitableprojectupc.entities.Grupo;
import com.unitable.unitableprojectupc.entities.UsuarioGrupo;
import com.unitable.unitableprojectupc.repository.GrupoRepository;
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

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation =Propagation.REQUIRED)
    public Grupo createGrupo(GrupoRequest grupoRequest) {
        Grupo newGrupo = initGrupo(grupoRequest);
        return grupoRepository.save(newGrupo);
    }

    private Grupo initGrupo(GrupoRequest grupoRequest) {
        GrupoValidator.validateGrupo(grupoRequest);
        Curso curso = cursoService.findCursoById(grupoRequest.getCurso_id());

        Grupo grupo = new Grupo();
        grupo.setNombre(grupoRequest.getNombre());
        grupo.setDescripcion(grupoRequest.getDescripcion());
        grupo.setFecha_creacion(Date.valueOf(LocalDate.now()));
        grupo.setFecha_fin(null);
        grupo.setTema(grupoRequest.getTema());
        grupo.setCurso(curso);
        grupo.setUsuarioGrupos(new ArrayList<UsuarioGrupo>());

        return grupo;
    }
}