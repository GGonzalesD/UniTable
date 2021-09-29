package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.dto.ActividadRequest;
import com.unitable.unitableprojectupc.entities.Actividad;
import com.unitable.unitableprojectupc.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Actividad createActividad(ActividadRequest actividadRequest) {
        Actividad newActividad = initActividad(actividadRequest);
        return actividadRepository.save(newActividad);
    }

    @Transactional(readOnly = true)
    public List<Actividad> findAllActividades() {
        List<Actividad> actividades = actividadRepository.findAll();
        return actividades;
    }

    private Actividad initActividad(ActividadRequest actividadRequest) {
        Actividad actividad = new Actividad();
        actividad.setNombre(actividadRequest.getNombre());
        actividad.setDetalles(actividadRequest.getDetalles());
        actividad.setFecha_ini(actividadRequest.getFecha_ini());
        actividad.setFecha_fin(actividadRequest.getFecha_fin());
        actividad.setDuracion_min(Integer.valueOf((int)(actividadRequest.getFecha_fin().getTime() - actividadRequest.getFecha_ini().getTime()))/60000);
        actividad.setActiva(Boolean.TRUE);
        return actividad;
    }

}
