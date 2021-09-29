package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.dto.CursoRequest;
import com.unitable.unitableprojectupc.dto.CursoResponse;
import com.unitable.unitableprojectupc.entities.Curso;
import com.unitable.unitableprojectupc.service.CursoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @PostMapping
    public ResponseEntity<CursoResponse> createCurso(@RequestBody CursoRequest cursoRequest) {
        Curso curso = cursoService.createCurso(cursoRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoCurso(curso), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll() {
        List<Curso> cursos = cursoService.findAllCursos();
        return new ResponseEntity<List<CursoResponse>>(
                entityDtoConverter.convertEntityToDtoCurso(cursos),
                HttpStatus.OK);
    }
}
