package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.common.ActividadValidator;
import com.unitable.unitableprojectupc.dto.ActividadRequest;
import com.unitable.unitableprojectupc.entities.Actividad;
import com.unitable.unitableprojectupc.entities.Recompensa;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.exception.BadResourceRequestException;
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

import java.util.Date;
import java.util.List;

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

        List<Actividad> actividades = findActividadesByUserId();

        if(actividadRequest.getFecha_fin().before(actividadRequest.getFecha_ini())){
            throw new BadResourceRequestException("La Fecha de Inicio esta despues de la Fecha de fin");
        }
        else if(actividadRequest.getFecha_ini().compareTo(actividadRequest.getFecha_fin()) == 0){
            throw new BadResourceRequestException("La Fecha de Inicio es igual a la Fecha de fin");
        }
        else if(actividades.size()==0){
            Actividad newActividad = initActividad(actividadRequest);
            return actividadRepository.save(newActividad);
        }
        else{
            for(int i = 0; i < actividades.size(); i++){
                Date FechaIni = actividades.get(i).getFecha_ini();
                Date FechaFin = actividades.get(i).getFecha_fin();

                if(actividadRequest.getFecha_ini().compareTo(FechaIni) == 0 || actividadRequest.getFecha_fin().compareTo(FechaFin) == 0){
                    throw new BadResourceRequestException("La Fecha de Inicio o la Fecha de Fin son iguales a las fechas de otra actividad");
                }
                else if(actividadRequest.getFecha_ini().after(FechaIni) == true && actividadRequest.getFecha_fin().before(FechaFin) == true){
                    throw new BadResourceRequestException("El rango de tiempo choca con otra actividad");
                }else if (actividadRequest.getFecha_ini().before(FechaIni) == true && actividadRequest.getFecha_fin().after(FechaFin) == true){
                    throw new BadResourceRequestException("El rango de tiempo choca con otra actividad");
                }else if(actividadRequest.getFecha_ini().after(FechaIni) == true && actividadRequest.getFecha_ini().before(FechaFin) == true || actividadRequest.getFecha_fin().after(FechaIni) == true && actividadRequest.getFecha_fin().before(FechaFin) == true){
                    throw new BadResourceRequestException("El rango de tiempo choca con otra actividad");
                }
            }
            Actividad newActividad = initActividad(actividadRequest);
            return actividadRepository.save(newActividad);
        }
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

        Usuario usuario = usuarioRepository.findUsuarioById(actividadFromDb.getUsuario().getId());

        if(actividadFromDb.getActiva()) {
            actividadFromDb.setActiva(Boolean.FALSE);
            usuario.setNum_act_completas(usuario.getNum_act_completas() + 1);
            usuarioRepository.save(usuario);
        }
        else {
            throw new BadResourceRequestException("Ya finalizó esta actividad");
        }

        if(usuario.getNum_act_completas() % 5 == 0)
        {
            Recompensa recompensa = Recompensa.builder()
                    .nombre("Finalizar 5 actividades")
                    .detalles("Recompensa acumulada " + (usuario.getNum_act_completas()/5))
                    .usuario(usuario)
                    .build();

            usuario.setNum_monedas(usuario.getNum_monedas() + 25);
            recompensaRepository.save(recompensa);
        }

        return actividadRepository.save(actividadFromDb);
    }

    @Transactional(readOnly = true, propagation=Propagation.REQUIRED)
    public List<Actividad> findActividadesByUserId() {
       List<Actividad> actividades = UserPrincipal.getCurrentUser().getActividades();
       return actividades;
    }
}
