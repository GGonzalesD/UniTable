package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.common.EntityDtoConverter;
import com.unitable.unitableprojectupc.dto.RecompensaRequest;
import com.unitable.unitableprojectupc.dto.RecompensaResponse;
import com.unitable.unitableprojectupc.entities.Recompensa;
import com.unitable.unitableprojectupc.service.RecompensaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/recompensas")
public class RecompensaController {

    @Autowired
    private RecompensaService recompensaService;

    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @PostMapping
    public ResponseEntity<RecompensaResponse> createRecompensa(@RequestBody RecompensaRequest recompensaRequest) {
        Recompensa recompensa = recompensaService.createRecompensa(recompensaRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoRecompensa(recompensa), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RecompensaResponse>> findAll() {
        List<Recompensa> recompensas = recompensaService.findAllRecompensas();
        return new ResponseEntity<List<RecompensaResponse>>(
                entityDtoConverter.convertEntityToDtoRecompensa(recompensas),
                HttpStatus.OK);
    }
}
