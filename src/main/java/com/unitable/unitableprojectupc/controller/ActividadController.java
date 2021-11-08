package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.dto.ActividadRequest;
import com.unitable.unitableprojectupc.dto.ActividadResponse;
import com.unitable.unitableprojectupc.entities.Actividad;
import com.unitable.unitableprojectupc.service.ActividadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @PostMapping("/agregar")
    public ResponseEntity<ActividadResponse> createActividad(@RequestBody ActividadRequest actividadRequest) throws Exception{
        Actividad actividad = actividadService.createActividad(actividadRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoActividad(actividad), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ActividadResponse>> findAll() throws Exception{
        List<Actividad> actividades = actividadService.findAllActividades();
        return new ResponseEntity<List<ActividadResponse>>(
                entityDtoConverter.convertEntityToDtoActividad(actividades),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActividadById(@PathVariable Long id) throws Exception{
        actividadService.deleteActividadById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActividadResponse> updateActividad(@PathVariable Long id,@RequestBody @Validated ActividadRequest actividadRequest) throws Exception{
        Actividad actividad = actividadService.updateActividad(id, actividadRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoActividad(actividad), HttpStatus.CREATED);
    }

    @PutMapping("/finish/{id}")
    public ResponseEntity<ActividadResponse> finishActividad(@PathVariable Long id) throws Exception{
        Actividad actividad = actividadService.finishActividad(id);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoActividad(actividad), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ActividadResponse>> findActividadesByUserId() throws Exception{
        List<Actividad> actividades = actividadService.findActividadesByUserId();
        return new ResponseEntity<List<ActividadResponse>>(
                entityDtoConverter.convertEntityToDtoActividad(actividades),
                HttpStatus.OK);
    }
}
