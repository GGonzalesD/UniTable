package com.unitable.unitableprojectupc.converters;

import com.unitable.unitableprojectupc.dto.CursoRequest;
import com.unitable.unitableprojectupc.dto.CursoResponse;
import com.unitable.unitableprojectupc.entities.Curso;

import org.springframework.stereotype.Component;

@Component
public class CursoConverter extends  AbstractConverter<Curso, CursoRequest, CursoResponse> {

    @Override
    public CursoResponse fromEntity(Curso curso) {
        if(curso == null) return null;
        return CursoResponse.builder()
                .id(curso.getId())
				.nombre(curso.getNombre())
				.descripcion(curso.getDescripcion())
                .build();
    }

    @Override
    public Curso fromRequest(CursoRequest dto) {
		if(dto == null) return null;
        return Curso.builder()
				.nombre(dto.getNombre())
				.descripcion(dto.getDescripcion())
				.build();
    }
}