package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.common.UserType;
import com.unitable.unitableprojectupc.dto.UsuarioRequest;
import com.unitable.unitableprojectupc.entities.Actividad;
import com.unitable.unitableprojectupc.entities.Recompensa;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.exception.UserNotFoundException;
import com.unitable.unitableprojectupc.repository.ActividadRepository;
import com.unitable.unitableprojectupc.repository.RecompensaRepository;
import com.unitable.unitableprojectupc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecompensaRepository recompensaRepository;

    @Autowired
    private ActividadRepository actividadRepository;

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

    @Transactional(readOnly = true)
    public Usuario findUsuarioById(Long id) {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findUsuarioById(id));
        return usuario.orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Recompensa> findRecompensasByUserId(Long id) {
        Optional<List<Recompensa>> recompensas = Optional.ofNullable(recompensaRepository.findRecompensasByUserId(id));
        return recompensas.orElseThrow(() -> new UserNotFoundException("id no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Actividad> findActividadesByUserId(Long id) {
        Optional<List<Actividad>> actividades = Optional.ofNullable(actividadRepository.findActividadesByUserId(id));
        return actividades.orElseThrow(() -> new UserNotFoundException("id no encontrado"));
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
        usuario.setRecompensas(new ArrayList<Recompensa>());
        usuario.setActividades(new ArrayList<Actividad>());
        return usuario;
    }
}
