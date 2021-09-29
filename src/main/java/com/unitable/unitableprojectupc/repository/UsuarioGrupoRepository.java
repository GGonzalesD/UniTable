package com.unitable.unitableprojectupc.repository;

import com.unitable.unitableprojectupc.entities.UsuarioGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, Long> {
}
