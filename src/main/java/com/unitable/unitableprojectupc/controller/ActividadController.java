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

    @PostMapping
    public ResponseEntity<ActividadResponse> createActividad(@RequestBody ActividadRequest actividadRequest) {
        Actividad actividad = actividadService.createActividad(actividadRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoActividad(actividad), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ActividadResponse>> findAll() {
        List<Actividad> actividades = actividadService.findAllActividades();
        return new ResponseEntity<List<ActividadResponse>>(
                entityDtoConverter.convertEntityToDtoActividad(actividades),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteActividadById(@PathVariable Long id){
        actividadService.deleteActividadById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActividadResponse> updateActividad(@PathVariable Long id,@RequestBody @Validated ActividadRequest actividadRequest){
        Actividad actividad = actividadService.updateActividad(id, actividadRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoActividad(actividad), HttpStatus.CREATED);
    }
}
