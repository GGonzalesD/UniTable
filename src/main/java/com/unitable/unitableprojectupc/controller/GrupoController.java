package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.dto.GrupoRequest;
import com.unitable.unitableprojectupc.dto.GrupoResponse;
import com.unitable.unitableprojectupc.entities.Grupo;
import com.unitable.unitableprojectupc.service.GrupoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api
@RestController
@RequestMapping("/grupos")

public class GrupoController {

    @Autowired
    private GrupoService grupoService;


    @Autowired
    private EntityDtoConverter entityDtoConverter;


    @PostMapping
    public ResponseEntity<GrupoResponse> createGrupo(@RequestBody GrupoRequest grupoRequest) {
        Grupo grupo = grupoService.createGrupo(grupoRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoGrupo(grupo), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<GrupoResponse>> findAll() {
        List<Grupo> grupos = grupoService.findAllGroups();
        return new ResponseEntity<List<GrupoResponse>>(
                entityDtoConverter.convertEntityToDtoGrupo(grupos),
                HttpStatus.OK);
    }




}
