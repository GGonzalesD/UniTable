package com.unitable.unitableprojectupc.controller;

import com.unitable.unitableprojectupc.converters.UsuarioConverter;
import com.unitable.unitableprojectupc.dto.LoginRequest;
import com.unitable.unitableprojectupc.dto.LoginResponse;
import com.unitable.unitableprojectupc.dto.UsuarioRequest;
import com.unitable.unitableprojectupc.dto.UsuarioResponse;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.service.UsuarioService;
import com.unitable.unitableprojectupc.utils.WrapperResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@Autowired
    private UsuarioService userService;
	@Autowired
	private UsuarioConverter usuarioConverter;



    @PostMapping("/signup")
    public ResponseEntity<WrapperResponse<UsuarioResponse>> signup(@RequestBody UsuarioRequest request){
		Usuario user = userService.createUser(request);
        return new WrapperResponse<>(true,"success", usuarioConverter.fromEntity(user))
                .createResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<WrapperResponse<LoginResponse>> login(@RequestBody LoginRequest request){
        LoginResponse response=userService.login(request);
        return new WrapperResponse<>(true,"success",response)
                .createResponse();
    }

}