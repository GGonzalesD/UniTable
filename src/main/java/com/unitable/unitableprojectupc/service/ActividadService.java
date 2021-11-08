package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.common.ActividadValidator;
import com.unitable.unitableprojectupc.dto.ActividadRequest;
import com.unitable.unitableprojectupc.entities.Actividad;
import com.unitable.unitableprojectupc.entities.Recompensa;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.exception.ResourceNotFoundException;
import com.unitable.unitableprojectupc.repository.ActividadRepository;
import com.unitable.unitableprojectupc.repository.RecompensaRepository;
import com.unitable.unitableprojectupc.repository.UsuarioRepository;
import com.unitable.unitableprojectupc.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecompensaRepository recompensaRepository;

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
        ActividadValidator.validateActividad(actividadRequest);

        Usuario usuario = UserPrincipal.getCurrentUser();

        Actividad actividad = new Actividad();
        actividad.setNombre(actividadRequest.getNombre());
        actividad.setDetalles(actividadRequest.getDetalles());
        actividad.setFecha_ini(actividadRequest.getFecha_ini());
        actividad.setFecha_fin(actividadRequest.getFecha_fin());
        actividad.setDuracion_min(Integer.valueOf((int)(actividadRequest.getFecha_fin().getTime() - actividadRequest.getFecha_ini().getTime()))/60000);
        actividad.setActiva(Boolean.TRUE);
        actividad.setUsuario(usuario);
        usuario.getActividades().add(actividad);
        return actividad;
    }

    @Transactional
    public void deleteActividadById(Long actividadId){
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(()-> ResourceNotFoundException.byIndex("Actividad", actividadId));
        actividadRepository.delete(actividad);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Actividad updateActividad(Long id, ActividadRequest actividadRequest){
        ActividadValidator.validateActividad(actividadRequest);
        Actividad actividadFromDb = actividadRepository.getById(id);


        actividadFromDb.setNombre(actividadRequest.getNombre());
        actividadFromDb.setDetalles(actividadRequest.getDetalles());
        actividadFromDb.setFecha_ini(actividadRequest.getFecha_ini());
        actividadFromDb.setFecha_fin(actividadRequest.getFecha_fin());
        actividadFromDb.setDuracion_min(Integer.valueOf((int)(actividadRequest.getFecha_fin().getTime() - actividadRequest.getFecha_ini().getTime()))/60000);

        return actividadRepository.save(actividadFromDb);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Actividad finishActividad(Long actividadId){
        Actividad actividadFromDb = actividadRepository.findById(actividadId)
                .orElseThrow(()-> ResourceNotFoundException.byIndex("Actividad", actividadId));

        actividadFromDb.setActiva(Boolean.FALSE);

        Usuario usuario = usuarioRepository.findUsuarioById(actividadFromDb.getUsuario().getId());
        usuario.setNum_act_completas(usuario.getNum_act_completas() + 1);
        usuarioRepository.save(usuario);

        if(usuario.getNum_act_completas() % 5 == 0)
        {
            Recompensa recompensa = Recompensa.builder()
                    .nombre("Finalizar 5 actividades")
                    .detalles("Recompensa acumulada " + (usuario.getNum_act_completas()/5))
                    .usuario(usuario)
                    .build();

            recompensaRepository.save(recompensa);
        }

        return actividadRepository.save(actividadFromDb);
    }

    @Transactional(readOnly = true)
    public List<Actividad> findActividadesByUserId() {
       List<Actividad> actividades = UserPrincipal.getCurrentUser().getActividades();
       return actividades;
    }
}
