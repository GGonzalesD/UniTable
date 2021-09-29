package com.unitable.unitableprojectupc.service;


import com.unitable.unitableprojectupc.dto.GrupoRequest;
import com.unitable.unitableprojectupc.dto.UsuarioRequest;
import com.unitable.unitableprojectupc.entities.Grupo;
import com.unitable.unitableprojectupc.entities.Recompensa;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.entities.UsuarioGrupo;
import com.unitable.unitableprojectupc.repository.GrupoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class GrupoService {
    @Autowired
    private GrupoInfo grupoInfo;

    @Transactional(readOnly = true)
    public List<Grupo> findAllGroups() {
        List<Grupo> grupos = grupoInfo.findAll();
        return grupos;
    }

    @Transactional(readOnly = true)
    public Grupo findGrupoById(Long id) {
        Optional<Grupo> grupo = Optional.ofNullable(grupoInfo.findGrupoById(id));
        return grupo.orElseThrow();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation =Propagation.REQUIRED)
    public Grupo createGrupo(GrupoRequest grupoRequest) {
        Grupo newGrupo = initGrupo(grupoRequest);
        return grupoInfo.save(newGrupo);
    }

    private Grupo initGrupo(GrupoRequest grupoRequest) {
        Grupo grupo = new Grupo();
        grupo.setNombre(grupoRequest.getNombre());
        grupo.setDescripcion(grupoRequest.getDescripcion());
        grupo.setFecha_creacion(Date.valueOf(LocalDate.now()));
        grupo.setFecha_fin(null);
        grupo.setTema(grupoRequest.getTema());
        grupo.setUsuarioGrupos(new ArrayList<UsuarioGrupo>());

        return grupo;
    }
}