package com.unitable.unitableprojectupc.repository;

import com.unitable.unitableprojectupc.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoInfo extends JpaRepository<Grupo, Long> {
    Grupo findGrupoById(Long id);

}