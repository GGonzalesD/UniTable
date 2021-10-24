package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.common.ActividadValidator;
import com.unitable.unitableprojectupc.dto.ActividadRequest;
import com.unitable.unitableprojectupc.entities.Actividad;
import com.unitable.unitableprojectupc.exception.ActividadNotFoundException;
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

    @Transactional
    public void deleteActividadById(Long actividadId){
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(()-> new ActividadNotFoundException("id no encontrado"));
        actividadRepository.delete(actividad);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Actividad updateActividad(Long id, ActividadRequest actividadRequest){
        ActividadValidator.validateActividad(actividadRequest);
        Actividad actividadFromDb = actividadRepository.getById(id);
        Actividad actividad = initActividad(actividadRequest);

        actividadFromDb.setNombre(actividad.getNombre());
        actividadFromDb.setDetalles(actividad.getDetalles());
        actividadFromDb.setFecha_fin(actividad.getFecha_ini());
        actividadFromDb.setFecha_fin(actividad.getFecha_fin());
        actividadFromDb.setDuracion_min(actividad.getDuracion_min());

        return actividadRepository.save(actividadFromDb);
    }
}
