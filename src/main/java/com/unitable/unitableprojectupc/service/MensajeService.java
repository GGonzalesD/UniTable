package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.dto.MensajeRequest;
import com.unitable.unitableprojectupc.entities.Mensaje;
import com.unitable.unitableprojectupc.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Mensaje createMensaje(MensajeRequest mensajeRequest) {
        Mensaje newMensaje = initMensaje(mensajeRequest);
        return mensajeRepository.save(newMensaje);
    }

    @Transactional(readOnly = true)
    public List<Mensaje> findAllMensajes() {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        return mensajes;
    }

    private Mensaje initMensaje(MensajeRequest mensajeRequest) {
        Mensaje mensaje = new Mensaje();
        mensaje.setMensaje(mensajeRequest.getMensaje());
        mensaje.setHora_mensaje(Time.valueOf(LocalTime.now()));
        return mensaje;
    }
}
