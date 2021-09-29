package com.unitable.unitableprojectupc.repository;

import com.unitable.unitableprojectupc.entities.Actividad;
import com.unitable.unitableprojectupc.entities.Recompensa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long>{

    @Query(value = "SELECT actividad FROM Actividad actividad WHERE actividad.usuario.id=?1")
    List<Actividad> findActividadesByUserId(Long userId);
}
