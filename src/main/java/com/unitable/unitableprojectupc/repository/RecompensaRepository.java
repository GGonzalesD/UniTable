package com.unitable.unitableprojectupc.repository;

import com.unitable.unitableprojectupc.entities.Recompensa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecompensaRepository extends JpaRepository<Recompensa, Long> {

    @Query(value = "SELECT recompensa FROM Recompensa recompensa WHERE recompensa.usuario.id=?1")
    List<Recompensa> findRecompensasByUserId(Long userId);
}
