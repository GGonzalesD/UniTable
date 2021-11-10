package com.unitable.unitableprojectupc.controller;


import com.unitable.unitableprojectupc.converters.MessageConverter;
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
    private MessageConverter messageConverter;

    @PostMapping
    public ResponseEntity<MensajeResponse> createMensaje(@RequestBody MensajeRequest mensajeRequest) throws Exception{
        Mensaje mensaje = mensajeService.createMensaje(mensajeRequest);
        return new ResponseEntity<>(messageConverter.fromEntity(mensaje), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MensajeResponse>> findAll() throws Exception{
        List<Mensaje> mensajes = mensajeService.findAllMensajes();
        return new ResponseEntity<List<MensajeResponse>>(
            messageConverter.fromEntity(mensajes),
                HttpStatus.OK);
    }
}
