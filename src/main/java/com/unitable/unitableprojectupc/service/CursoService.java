package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.dto.CursoRequest;
import com.unitable.unitableprojectupc.entities.Curso;
import com.unitable.unitableprojectupc.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Curso createCurso(CursoRequest cursoRequest) {
        Curso newCurso = initCurso(cursoRequest);
        return cursoRepository.save(newCurso);
    }

    @Transactional(readOnly = true)
    public List<Curso> findAllCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos;
    }

    private Curso initCurso(CursoRequest cursoRequest) {
        Curso curso = new Curso();
        curso.setNombre(cursoRequest.getNombre());
        curso.setDescripcion(cursoRequest.getDescripcion());
        return curso;
    }
}
