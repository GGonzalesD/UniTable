package com.unitable.unitableprojectupc.repository;

import java.util.List;

import com.unitable.unitableprojectupc.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    Grupo findGrupoById(Long id);

    @Query(value="SELECT gs.* FROM usuario_grupo ug JOIN usuarios us ON ug.user_id=us.id JOIN grupos gs ON ug.grupo_id=gs.id WHERE us.id=?1 AND gs.curso_id=?2", nativeQuery = true)
    List<Grupo> findByUsuarioAndCurso(Long usuarioId, Long cursoId);
}