package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.converters.UsuarioConverter;
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
    private UsuarioConverter usuarConverter;

    @Autowired
    private EntityDtoConverter entityDtoConverter;


    @PostMapping
    public ResponseEntity<UsuarioResponse> createUser(@RequestBody UsuarioRequest usuarioRequest) throws Exception{
        Usuario usuario = usuarioService.createUser(usuarioRequest);
        return new ResponseEntity<>(usuarConverter.fromEntity(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/follows/{id}")
    public ResponseEntity<List<UsuarioResponse>> getContactos(@PathVariable Long id) throws Exception{
        List<Usuario> usuarios = usuarioService.getContactos(id);
        return new ResponseEntity<>(usuarConverter.fromEntity(usuarios),
                HttpStatus.OK);
    }

    @PutMapping("/follow")
    public ResponseEntity<Boolean> followToUsuario(@RequestParam Long usId, @RequestParam Long fwId) throws Exception{
        Boolean following = usuarioService.followToUser(usId, fwId);
        return new ResponseEntity<>(following,
                HttpStatus.OK);
    }

    @PutMapping("/join")
    public ResponseEntity<List<GrupoResponse>> joinToOneGroup(@RequestParam Long userId, @RequestParam Long groupId) throws Exception{
        List<Grupo> grupos = usuarioService.joinToAGroup(userId, groupId);

        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoGrupo(grupos),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() throws Exception{
        List<Usuario> usuarios = usuarioService.findAllUsers();
        return new ResponseEntity<List<UsuarioResponse>>(
            usuarConverter.fromEntity(usuarios),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findUsuarioById(@PathVariable Long id) throws Exception{
        Usuario usuario = usuarioService.findUsuarioById(id);
        return new ResponseEntity<>(usuarConverter.fromEntity(usuario),
                HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<UsuarioResponse> findInfoUser() throws Exception{
        Usuario usuario = usuarioService.findUsuario();
        return new ResponseEntity<>(usuarConverter.fromEntity(usuario),
                HttpStatus.OK);
    }

    @GetMapping("/userinfo")
    public ResponseEntity<List<UsuarioResponse>> findUsuarioByNombresAndApellidos(@RequestParam String nombres, @RequestParam String apellidos) throws Exception{
        List<Usuario> usuarios = usuarioService.finUsuarioByNombresAndApellidos(nombres, apellidos);
        return new ResponseEntity<>(usuarConverter.fromEntity(usuarios),
                HttpStatus.OK);
    }

    @GetMapping("/userlogin")
    public ResponseEntity<UsuarioResponse> findUsuarioByCorreoAndPassword(@RequestParam String correo, @RequestParam String password) throws Exception{
        Usuario usuario = usuarioService.finUsuarioByCorreoAndPassword(correo, password);
        return new ResponseEntity<>(usuarConverter.fromEntity(usuario),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuariobyId(@PathVariable Long id, @RequestBody UsuarioRequest usuarioRequest) throws Exception{
        Usuario usuario = usuarioService.updateUsuarioById(id, usuarioRequest);
        return new ResponseEntity<>(usuarConverter.fromEntity(usuario),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioById(@PathVariable Long id) throws Exception{
        usuarioService.deleteUsuarioById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/{id}/recompensas")
    public ResponseEntity<List<RecompensaResponse>> findRecompensasByUserId(@PathVariable Long id) throws Exception{
        List<Recompensa> recompensas = usuarioService.findRecompensasByUserId(id);
        return new ResponseEntity<List<RecompensaResponse>>(
                entityDtoConverter.convertEntityToDtoRecompensa(recompensas),
                HttpStatus.OK);
    }
}
