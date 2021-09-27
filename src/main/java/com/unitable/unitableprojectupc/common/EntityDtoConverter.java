package com.unitable.unitableprojectupc.common;

import com.unitable.unitableprojectupc.dto.UsuarioResponse;
import com.unitable.unitableprojectupc.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioResponse convertEntityToDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    public List<UsuarioResponse> convertEntityToDto(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
