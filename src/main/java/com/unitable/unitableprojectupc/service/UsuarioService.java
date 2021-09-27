package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.common.UserType;
import com.unitable.unitableprojectupc.dto.UsuarioRequest;
import com.unitable.unitableprojectupc.dto.UsuarioResponse;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Usuario createUser(UsuarioRequest usuarioRequest) {
        Usuario newUser = initUsuario(usuarioRequest);
        return usuarioRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAllUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    private Usuario initUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setNombres(usuarioRequest.getNombres());
        usuario.setApellidos(usuarioRequest.getApellidos());
        usuario.setCorreo(usuarioRequest.getCorreo());
        usuario.setPassword(usuarioRequest.getPassword());
        usuario.setCarrera(usuarioRequest.getCarrera());
        usuario.setNum_act_completas(0);
        usuario.setNum_monedas(0);
        usuario.setIsPremium(false);
        usuario.setTipo_usuario(UserType.ESTUDIANTE);
        return usuario;
    }
}
