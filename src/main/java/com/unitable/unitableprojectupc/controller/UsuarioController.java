package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.dto.UsuarioRequest;
import com.unitable.unitableprojectupc.dto.UsuarioResponse;
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
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDto(usuario), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {
        List<Usuario> usuarios = usuarioService.findAllUsers();
        return new ResponseEntity<List<UsuarioResponse>>(
                entityDtoConverter.convertEntityToDto(usuarios),
                HttpStatus.OK);
    }
}
