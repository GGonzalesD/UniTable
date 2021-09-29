package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.dto.UsuarioGrupoRequest;
import com.unitable.unitableprojectupc.entities.UsuarioGrupo;
import com.unitable.unitableprojectupc.repository.UsuarioGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioGrupoService {

    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public UsuarioGrupo createUsuarioGrupo(UsuarioGrupoRequest usuarioGrupoRequest) {
        UsuarioGrupo newUsuarioGrupo = initUsuarioGrupo(usuarioGrupoRequest);
        return usuarioGrupoRepository.save(newUsuarioGrupo);
    }

    @Transactional(readOnly = true)
    public List<UsuarioGrupo> findAllUsuarioGrupos() {
        List<UsuarioGrupo> usuarioGrupos = usuarioGrupoRepository.findAll();
        return usuarioGrupos;
    }

    private UsuarioGrupo initUsuarioGrupo(UsuarioGrupoRequest usuarioGrupoRequest) {
        UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
        usuarioGrupo.setDescripcion(usuarioGrupoRequest.getDescripcion());
        usuarioGrupo.setNum_usuarios(Integer.valueOf(0));
        return usuarioGrupo;
    }
}
