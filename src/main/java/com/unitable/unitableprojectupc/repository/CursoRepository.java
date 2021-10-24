package com.unitable.unitableprojectupc.repository;

import com.unitable.unitableprojectupc.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
	Curso findCursoById(Long id);
}
