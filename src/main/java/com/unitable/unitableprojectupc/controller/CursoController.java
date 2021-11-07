package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.converters.CursoConverter;
import com.unitable.unitableprojectupc.dto.CursoRequest;
import com.unitable.unitableprojectupc.dto.CursoResponse;
import com.unitable.unitableprojectupc.entities.Curso;
import com.unitable.unitableprojectupc.service.CursoService;
import com.unitable.unitableprojectupc.utils.WrapperResponse;

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
    private CursoConverter cursoConverter;

    @PostMapping
    public ResponseEntity<CursoResponse> createCurso(@RequestBody CursoRequest cursoRequest) throws Exception{
        Curso curso = cursoService.createCurso(cursoRequest);
        return new ResponseEntity<>(cursoConverter.fromEntity(curso), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll() throws Exception{
        List<Curso> cursos = cursoService.findAllCursos();
        return new ResponseEntity<List<CursoResponse>>(
            cursoConverter.fromEntity(cursos),
                HttpStatus.OK);
    }

    @GetMapping("/byname")
    public ResponseEntity<WrapperResponse<CursoResponse>> findByName(@RequestParam String name){
        Curso curso = cursoService.findCursoByNombre(name);
        return new WrapperResponse<>(true,"success", cursoConverter.fromEntity(curso))
        .createResponse();
    }

    @GetMapping("/auto")
    public ResponseEntity<List<CursoResponse>> findAutocomplete(@RequestParam String search){
        List<Curso> cursos = cursoService.findCursoByNombrePart(search);
        return new ResponseEntity<List<CursoResponse>>(
            cursoConverter.fromEntity(cursos),
            HttpStatus.OK);
    }
}
