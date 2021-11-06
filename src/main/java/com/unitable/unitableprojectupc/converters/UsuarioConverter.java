package com.unitable.unitableprojectupc.converters;

import com.unitable.unitableprojectupc.dto.LoginRequest;
import com.unitable.unitableprojectupc.dto.UsuarioRequest;
import com.unitable.unitableprojectupc.dto.UsuarioResponse;
import com.unitable.unitableprojectupc.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter extends  AbstractConverter<Usuario, UsuarioRequest, UsuarioResponse> {

    @Override
    public UsuarioResponse fromEntity(Usuario usuario) {
        if(usuario == null) return null;
        return UsuarioResponse.builder()
                .id(usuario.getId())
				.correo(usuario.getCorreo())
				.nombres(usuario.getNombres())
				.apellidos(usuario.getApellidos())
				.carrera(usuario.getCarrera())
				.num_act_completas(usuario.getNum_act_completas())
				.num_monedas(usuario.getNum_monedas())
				.tipo_usuario(usuario.getTipo_usuario())
                .build();
    }

    @Override
    public Usuario fromRequest(UsuarioRequest dto) {
		if(dto == null) return null;
        return Usuario.builder()
                .correo(dto.getCorreo())
                .nombres(dto.getNombres())
				.apellidos(dto.getApellidos())
				.carrera(dto.getCarrera())
				.tipo_usuario(dto.getTipo_usuario())
				.build();
    }

    public Usuario login(LoginRequest dto){
        if(dto == null) return null;
        return Usuario.builder()
                .correo(dto.getCorreo())
                .password(dto.getPassword())
                .build();
    }
}