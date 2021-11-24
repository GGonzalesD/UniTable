package com.unitable.unitableprojectupc.repository;

import java.util.Optional;
import com.unitable.unitableprojectupc.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT usuario FROM Usuario usuario WHERE usuario.nombres=?1 AND usuario.apellidos=?2")
    List<Usuario> finUsuarioByNombresAndApellidos(String nombres, String apellidos);

    @Query(value = "SELECT * FROM usuarios usuario WHERE usuario.correo=?1 AND usuario.password=?2", nativeQuery = true)
    Usuario findUsuarioByCorreoAndPassword(String correo, String password);

    @Query(value = "SELECT * FROM usuarios usuario WHERE usuario.correo=?1", nativeQuery = true)
    Usuario findUsuarioByCorreo(String correo);

    

    Usuario findUsuarioById(Long id);

    @Query(value = "SELECT * FROM usuarios usuario WHERE usuario.correo=?1", nativeQuery = true)
    Optional<Usuario> findByCorreo(String correo);

    @Query(value = "DELETE * FROM usuario_contactos usuario WHERE usuario.user_id=?1 OR usuario.contacto_id=?1", nativeQuery = true)
    void deleteContactos(Long id);

    @Query(value = "DELETE * FROM usuario_grupo usuario WHERE usuario.user_id=?1", nativeQuery = true)
    void deleteGrupos(Long id);
}
