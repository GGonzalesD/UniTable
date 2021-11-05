package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.common.UsuarioValidator;
import com.unitable.unitableprojectupc.dto.UsuarioRequest;
import com.unitable.unitableprojectupc.entities.*;
import com.unitable.unitableprojectupc.exception.GrupoNotFoundException;
import com.unitable.unitableprojectupc.exception.UserNotFoundException;
import com.unitable.unitableprojectupc.repository.ActividadRepository;
import com.unitable.unitableprojectupc.repository.GrupoRepository;
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

    @Autowired
    private GrupoRepository grupoRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Usuario createUser(UsuarioRequest usuarioRequest) {
        UsuarioValidator.validateUser(usuarioRequest);
        Usuario newUser = initUsuario(usuarioRequest);
        return usuarioRepository.save(newUser);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Grupo> joinToAGroup(Long userId, Long groupId) {
        
        
        Usuario usuario = usuarioRepository.findById(userId)
			.orElseThrow( () -> UserNotFoundException.byIndex(userId) );

        Grupo grupo = grupoRepository.findById(groupId)
            .orElseThrow( () -> GrupoNotFoundException.byIndex(groupId) );

        if(usuario.getGrupos().contains(grupo) == false) {
            usuario.getGrupos().add(grupo);
            grupo.getUsuarios().add(usuario);
        }

        usuarioRepository.save(usuario);
        return usuario.getGrupos();
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAllUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    @Transactional(readOnly = true)
    public Usuario findUsuarioById(Long userId) {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findUsuarioById(userId));
        return usuario.orElseThrow(() -> UserNotFoundException.byIndex(userId) );
    }

    @Transactional(readOnly = true)
    public List<Usuario> finUsuarioByNombresAndApellidos(String nombres, String apellidos) {
        Optional<List<Usuario>> usuarios = Optional.ofNullable(usuarioRepository.finUsuarioByNombresAndApellidos(nombres, apellidos));
        return usuarios.orElseThrow(() -> new UserNotFoundException("No se encontro al usuario"));
    }

    @Transactional(readOnly = true)
    public Usuario finUsuarioByCorreoAndPassword(String correo, String password) {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findUsuarioByCorreoAndPassword(correo, password));
        return usuario.orElseThrow(() -> new UserNotFoundException("No se encontro al usuario"));
    }

    @Transactional(readOnly = true)
    public List<Usuario> getContactos(Long userId){
        Usuario usuario = usuarioRepository.findById(userId).
            orElseThrow(() -> UserNotFoundException.byIndex(userId) );
        
        return usuario.getContactos();
    }

    @Transactional
    public Boolean followToUser(Long userId, Long followedId) {
        UsuarioValidator.validateFollow(userId, followedId);
        Usuario usuario = usuarioRepository.findById(userId).
            orElseThrow(() -> UserNotFoundException.byIndex(userId) );
        Usuario followed = usuarioRepository.findById(followedId).
            orElseThrow(() -> UserNotFoundException.byIndex(followedId) );

        if(usuario.getContactos().contains(followed))
            usuario.getContactos().remove(followed);
        else
            usuario.getContactos().add(followed);
        
        usuarioRepository.save(usuario);
        return usuario.getContactos().contains(followed);

    }

    @Transactional
    public Usuario updateUsuarioById(Long userId, UsuarioRequest usuarioRequest) {
        UsuarioValidator.validateUser(usuarioRequest);
        Usuario usuario = usuarioRepository.findById(userId).
                orElseThrow(() -> UserNotFoundException.byIndex(userId) );
        usuario.setNombres(usuarioRequest.getNombres());
        usuario.setApellidos(usuarioRequest.getApellidos());
        usuario.setCorreo(usuarioRequest.getCorreo());
        usuario.setPassword(usuarioRequest.getPassword());
        usuario.setTipo_usuario(usuarioRequest.getTipo_usuario());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario deleteUsuarioById(Long userId){
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(()-> UserNotFoundException.byIndex(userId) );
        usuarioRepository.delete(usuario);
        return usuario;
    }

    @Transactional(readOnly = true)
    public List<Recompensa> findRecompensasByUserId(Long userId) {
        Optional<List<Recompensa>> recompensas = Optional.ofNullable(recompensaRepository.findRecompensasByUserId(userId));
        return recompensas.orElseThrow(() -> UserNotFoundException.byIndex(userId) );
    }

    @Transactional(readOnly = true)
    public List<Actividad> findActividadesByUserId(Long userId) {
        Optional<List<Actividad>> actividades = Optional.ofNullable(actividadRepository.findActividadesByUserId(userId));
        return actividades.orElseThrow(() -> UserNotFoundException.byIndex(userId) );
    }



    private Usuario initUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setNombres(usuarioRequest.getNombres());
        usuario.setApellidos(usuarioRequest.getApellidos());
        usuario.setCorreo(usuarioRequest.getCorreo());
        usuario.setPassword(usuarioRequest.getPassword());
        usuario.setCarrera(usuarioRequest.getCarrera());
        usuario.setNum_act_completas(Integer.valueOf(0));
        usuario.setNum_monedas(Integer.valueOf(0));
        usuario.setIsPremium(Boolean.FALSE);
        usuario.setTipo_usuario(usuarioRequest.getTipo_usuario());
        usuario.setRecompensas(new ArrayList<Recompensa>());
        usuario.setActividades(new ArrayList<Actividad>());
        usuario.setMensajes(new ArrayList<Mensaje>());
        usuario.setGrupos(new ArrayList<Grupo>());
        return usuario;
    }
}
