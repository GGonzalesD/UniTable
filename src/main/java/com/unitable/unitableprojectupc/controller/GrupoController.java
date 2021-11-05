package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.dto.GrupoRequest;
import com.unitable.unitableprojectupc.dto.GrupoResponse;
import com.unitable.unitableprojectupc.entities.Grupo;
import com.unitable.unitableprojectupc.service.GrupoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/{id}")
    public ResponseEntity<GrupoResponse> findGrupoById(@PathVariable Long id) {
        Grupo grupo = grupoService.findGrupoById(id);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoGrupo(grupo),
                HttpStatus.OK);
    }

    @GetMapping("/filter_cursos")
    public ResponseEntity<List<GrupoResponse>> findGrupoByUsuarioAndCurso(@RequestParam Long userId, @RequestParam Long cursoId) {
        List<Grupo> grupos = grupoService.findgrupoByUsuarioAndCurso(userId, cursoId);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoGrupo(grupos),
                HttpStatus.OK);
    }

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

    @GetMapping("/page")
    public Page<GrupoResponse> findAllPage(@PageableDefault(sort="nombre", size=3, direction = Sort.Direction.ASC)Pageable pageable) {
        return grupoService.findAllGroupsPage(pageable);
    }


}
