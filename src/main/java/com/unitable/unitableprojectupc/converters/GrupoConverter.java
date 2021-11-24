package com.unitable.unitableprojectupc.converters;

import com.unitable.unitableprojectupc.dto.GrupoRequest;
import com.unitable.unitableprojectupc.dto.GrupoResponse;
import com.unitable.unitableprojectupc.entities.Grupo;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GrupoConverter extends  AbstractConverter<Grupo, GrupoRequest, GrupoResponse> {
    
	@Autowired
	private ChatConverter chatConverter;

	@Override
    public GrupoResponse fromEntity(Grupo grupo) {
        if(grupo == null) return null;
		Usuario usuario = UserPrincipal.getCurrentUser();
        return GrupoResponse.builder()
				.id(grupo.getId())
				.nombre(grupo.getNombre())
				.tema(grupo.getTema())
				.descripcion(grupo.getDescripcion())
				.fecha_creacion(grupo.getFecha_creacion())
				.fecha_fin(grupo.getFecha_fin())
				.chat(chatConverter.fromEntity(grupo.getChat()))
				.curso(grupo.getCurso())
				.usuario_grupo( grupo.getUsuarios().stream().filter( x -> x.getId() == usuario.getId() ).findFirst().orElse(null) != null )
                .build();
    }

    @Override
    public Grupo fromRequest(GrupoRequest dto) {
		if(dto == null) return null;
        return Grupo.builder()
				.nombre(dto.getNombre())
				.tema(dto.getTema())
				.descripcion(dto.getDescripcion())
				.build();
    }
}
