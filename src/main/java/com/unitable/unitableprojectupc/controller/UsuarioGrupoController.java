package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.dto.UsuarioGrupoRequest;
import com.unitable.unitableprojectupc.dto.UsuarioGrupoResponse;
import com.unitable.unitableprojectupc.entities.UsuarioGrupo;
import com.unitable.unitableprojectupc.service.UsuarioGrupoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/usuarioGrupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @PostMapping
    public ResponseEntity<UsuarioGrupoResponse> createUsuarioGrupo(@RequestBody UsuarioGrupoRequest usuarioGrupoRequest) {
        UsuarioGrupo usuarioGrupo = usuarioGrupoService.createUsuarioGrupo(usuarioGrupoRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoUsuarioGrupo(usuarioGrupo), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioGrupoResponse>> findAll() {
        List<UsuarioGrupo> usuarioGrupos = usuarioGrupoService.findAllUsuarioGrupos();
        return new ResponseEntity<List<UsuarioGrupoResponse>>(
                entityDtoConverter.convertEntityToDtoUsuarioGrupo(usuarioGrupos),
                HttpStatus.OK);
    }
}
