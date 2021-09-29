package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.dto.MensajeRequest;
import com.unitable.unitableprojectupc.dto.MensajeResponse;
import com.unitable.unitableprojectupc.entities.Mensaje;
import com.unitable.unitableprojectupc.service.MensajeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @PostMapping
    public ResponseEntity<MensajeResponse> createMensaje(@RequestBody MensajeRequest mensajeRequest) {
        Mensaje mensaje = mensajeService.createMensaje(mensajeRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoMensaje(mensaje), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MensajeResponse>> findAll() {
        List<Mensaje> mensajes = mensajeService.findAllMensajes();
        return new ResponseEntity<List<MensajeResponse>>(
                entityDtoConverter.convertEntityToDtoMensaje(mensajes),
                HttpStatus.OK);
    }
}
