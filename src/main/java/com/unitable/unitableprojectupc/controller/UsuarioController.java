package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.dto.ActividadResponse;
import com.unitable.unitableprojectupc.dto.GrupoResponse;
import com.unitable.unitableprojectupc.dto.RecompensaResponse;
import com.unitable.unitableprojectupc.dto.UsuarioRequest;
import com.unitable.unitableprojectupc.dto.UsuarioResponse;
import com.unitable.unitableprojectupc.entities.Actividad;
import com.unitable.unitableprojectupc.entities.Grupo;
import com.unitable.unitableprojectupc.entities.Recompensa;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.service.UsuarioService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EntityDtoConverter entityDtoConverter;


    @PostMapping
    public ResponseEntity<UsuarioResponse> createUser(@RequestBody UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioService.createUser(usuarioRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoUser(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/join")
    public ResponseEntity<List<GrupoResponse>> joinToOneGroup(@RequestParam Long userId, @RequestParam Long groupId) {
        List<Grupo> grupos = usuarioService.joinToAGroup(userId, groupId);

        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoGrupo(grupos),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {
        List<Usuario> usuarios = usuarioService.findAllUsers();
        return new ResponseEntity<List<UsuarioResponse>>(
                entityDtoConverter.convertEntityToDtoUser(usuarios),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findUsuarioById(id);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoUser(usuario),
                HttpStatus.OK);
    }

    @GetMapping("/userinfo")
    public ResponseEntity<List<UsuarioResponse>> findUsuarioByNombresAndApellidos(@RequestParam String nombres, @RequestParam String apellidos) {
        List<Usuario> usuarios = usuarioService.finUsuarioByNombresAndApellidos(nombres, apellidos);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoUser(usuarios),
                HttpStatus.OK);
    }

    @GetMapping("/userlogin")
    public ResponseEntity<UsuarioResponse> findUsuarioByCorreoAndPassword(@RequestParam String correo, @RequestParam String password) {
        Usuario usuario = usuarioService.finUsuarioByCorreoAndPassword(correo, password);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoUser(usuario),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuariobyId(@PathVariable Long id, @RequestBody UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioService.updateUsuarioById(id, usuarioRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoUser(usuario),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponse> deleteUsuarioById(@PathVariable Long id){
        Usuario usuario = usuarioService.deleteUsuarioById(id);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoUser(usuario),
                HttpStatus.OK);
    }

    @GetMapping("/{id)/recompensas")
    public ResponseEntity<List<RecompensaResponse>> findRecompensasByUserId(@PathVariable Long id) {
        List<Recompensa> recompensas = usuarioService.findRecompensasByUserId(id);
        return new ResponseEntity<List<RecompensaResponse>>(
                entityDtoConverter.convertEntityToDtoRecompensa(recompensas),
                HttpStatus.OK);
    }

    @GetMapping("/{id)/actividades")
    public ResponseEntity<List<ActividadResponse>> findActividadesByUserId(@PathVariable Long id) {
        List<Actividad> actividades = usuarioService.findActividadesByUserId(id);
        return new ResponseEntity<List<ActividadResponse>>(
                entityDtoConverter.convertEntityToDtoActividad(actividades),
                HttpStatus.OK);
    }
}
