package com.unitable.unitableprojectupc.repository;

import java.util.List;
import com.unitable.unitableprojectupc.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
	Curso findCursoById(Long id);

	@Query(value = "SELECT * FROM cursos curso WHERE curso.nombre=?1", nativeQuery = true)
    Curso findByNombre(@Param("nombre") String nombre);

	@Query(value = "SELECT * FROM cursos curso WHERE LOWER(curso.nombre) LIKE %:nombre% ORDER BY nombre DESC LIMIT 10", nativeQuery = true)
    List<Curso> findByNombreLike(@Param("nombre") String nombre);
}
