package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.common.UsuarioValidator;
import com.unitable.unitableprojectupc.converters.UsuarioConverter;
import com.unitable.unitableprojectupc.dto.LoginRequest;
import com.unitable.unitableprojectupc.dto.LoginResponse;
import com.unitable.unitableprojectupc.dto.UsuarioRequest;
import com.unitable.unitableprojectupc.entities.*;
import com.unitable.unitableprojectupc.exception.BadResourceRequestException;
import com.unitable.unitableprojectupc.exception.GeneralServiceException;
import com.unitable.unitableprojectupc.exception.ResourceNotFoundException;
import com.unitable.unitableprojectupc.repository.GrupoRepository;
import com.unitable.unitableprojectupc.repository.RecompensaRepository;
import com.unitable.unitableprojectupc.repository.UsuarioRepository;
import com.unitable.unitableprojectupc.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsuarioService {

    @Value("${jwt.password}")
    private String jwtSecret;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecompensaRepository recompensaRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Usuario createUser(UsuarioRequest usuarioRequest) {
        
        try {
            Usuario user = initUsuario(usuarioRequest);
            UsuarioValidator.validateUser(usuarioRequest);
            Usuario existUser=usuarioRepository.findByCorreo(user.getCorreo())
                    .orElse(null);
            if(existUser!=null)
                throw new BadResourceRequestException("Ya se registro una cuenta con este correo.");

            String encoder = passwordEncoder.encode(user.getPassword());
            user.setPassword(encoder);

            return usuarioRepository.save(user);
        } catch (BadResourceRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Grupo> joinToAGroup(Long userId, Long groupId) {
        
        
        Usuario usuario = usuarioRepository.findById(userId)
			.orElseThrow( () -> ResourceNotFoundException.byIndex("Usuario", userId));

        Grupo grupo = grupoRepository.findById(groupId)
            .orElseThrow( () -> ResourceNotFoundException.byIndex("Grupo", groupId));

        if(usuario.getGrupos().contains(grupo) == false) {
            usuario.getGrupos().add(grupo);
            grupo.getUsuarios().add(usuario);
        }

        usuarioRepository.save(usuario);
        return usuario.getGrupos();
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAllUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    @Transactional(readOnly = true)
    public Usuario findUsuarioById(Long userId) {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findUsuarioById(userId));
        return usuario.orElseThrow(() -> ResourceNotFoundException.byIndex("Usuario", userId) );
    }

    @Transactional(readOnly = true)
    public List<Usuario> finUsuarioByNombresAndApellidos(String nombres, String apellidos) {
        Optional<List<Usuario>> usuarios = Optional.ofNullable(usuarioRepository.finUsuarioByNombresAndApellidos(nombres, apellidos));
        return usuarios.orElseThrow(() -> new ResourceNotFoundException("No se encontro al usuario con nombre "+ nombres));
    }

    @Transactional(readOnly = true)
    public Usuario finUsuarioByCorreoAndPassword(String correo, String password) {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findUsuarioByCorreoAndPassword(correo, password));
        return usuario.orElseThrow(() -> new ResourceNotFoundException("El correro la contraseña estan mal"));
    }
    @Transactional(readOnly = true)
    public Usuario findUsuario() {
        Usuario usuario = UserPrincipal.getCurrentUser();
        return usuario;
    }

    @Transactional(readOnly = true)
    public List<Usuario> getContactos(){

        Usuario usuario = UserPrincipal.getCurrentUser();
        
        return usuario.getContactos();
    }

    @Transactional
    public Boolean followToUser(Long followedId) {

        Usuario usuario = UserPrincipal.getCurrentUser();

        UsuarioValidator.validateFollow(usuario.getId(), followedId);
        Usuario followed = usuarioRepository.findById(followedId).
            orElseThrow(() -> ResourceNotFoundException.byIndex("Usuario", followedId) );

        if(usuario.getContactos().contains(followed))
            usuario.getContactos().remove(followed);
        else
            usuario.getContactos().add(followed);
        
        usuarioRepository.save(usuario);
        return usuario.getContactos().contains(followed);

    }

    @Transactional
    public Usuario updateUsuarioById(UsuarioRequest usuarioRequest) {
        UsuarioValidator.validateUser(usuarioRequest);

        Usuario usuario = UserPrincipal.getCurrentUser();

        usuario.setNombres(usuarioRequest.getNombres());
        usuario.setApellidos(usuarioRequest.getApellidos());
        usuario.setCorreo(usuarioRequest.getCorreo());

        String encoder = passwordEncoder.encode(usuarioRequest.getPassword());
        usuario.setPassword(encoder);

        usuario.setTipo_usuario(usuarioRequest.getTipo_usuario());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteUsuarioById(){
        Usuario usuario = UserPrincipal.getCurrentUser();
        
        usuario.getGrupos().forEach((Grupo grupo)->{
            grupo.getUsuarios().remove(usuario);
            usuario.getGrupos().remove(grupo);
        });

        usuario.getContactos().forEach((Usuario userFollow) -> {
            userFollow.getContactos().remove(usuario);
        });

        usuarioRepository.delete(usuario);
    }

    @Transactional(readOnly = true)
    public List<Recompensa> findRecompensasByUserId() {
        Usuario usuario = UserPrincipal.getCurrentUser();

        Optional<List<Recompensa>> recompensas = Optional.ofNullable(recompensaRepository.findRecompensasByUserId(usuario.getId()));
        return recompensas.orElseThrow(() -> ResourceNotFoundException.byIndex("Usuario", usuario.getId()) );
    }

    public LoginResponse login(LoginRequest request){
        try {
            Usuario user=usuarioRepository.findByCorreo(request.getCorreo())
                    .orElseThrow(()->new BadResourceRequestException("Usuario o password incorrecto"));

            if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
                throw new BadResourceRequestException("Usuario o password incorrecto");

            String token = createToken(user);

            return new LoginResponse(usuarioConverter.fromEntity(user),token);

        } catch (BadResourceRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public String createToken(Usuario user){
        Date now =new Date();
        Date expiryDate=new Date(now.getTime()+ (1000*60*60*24));

        return Jwts.builder()
                .setSubject(user.getCorreo())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (UnsupportedJwtException e) {
            log.error("JWT in a particular format/configuration that does not match the format expected");
        }catch (MalformedJwtException e) {
            log.error(" JWT was not correctly constructed and should be rejected");
        }catch (SignatureException e) {
            log.error("Signature or verifying an existing signature of a JWT failed");
        }catch (ExpiredJwtException e) {
            log.error("JWT was accepted after it expired and must be rejected");
        }
        return false;
    }

    public String getUsernameFromToken(String jwt) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
        } catch (Exception e) {
            throw new BadResourceRequestException("Invalid Token");
        }
    }


    private Usuario initUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setNombres(usuarioRequest.getNombres());
        usuario.setApellidos(usuarioRequest.getApellidos());
        usuario.setCorreo(usuarioRequest.getCorreo());
        usuario.setPassword(usuarioRequest.getPassword());
        usuario.setCarrera(usuarioRequest.getCarrera());
        usuario.setNum_act_completas(Integer.valueOf(0));
        usuario.setNum_monedas(Integer.valueOf(0));
        usuario.setIsPremium(Boolean.FALSE);
        usuario.setTipo_usuario(usuarioRequest.getTipo_usuario());
        usuario.setRecompensas(new ArrayList<Recompensa>());
        usuario.setActividades(new ArrayList<Actividad>());
        usuario.setMensajes(new ArrayList<Mensaje>());
        usuario.setGrupos(new ArrayList<Grupo>());
        return usuario;
    }
}
