package com.unitable.unitableprojectupc.repository;

import java.util.List;

import com.unitable.unitableprojectupc.entities.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

	@Query(value = "select (count(*)) as quantity, to_char(m.hora_mensaje, 'HH24') as datecreate from mensajes m where m.chat_id=?1 group by to_char(m.hora_mensaje, 'HH24') order by to_char(m.hora_mensaje, 'HH24') asc", nativeQuery = true)
    List<Object[]> findMessagePerChat(Long chatId);
}
