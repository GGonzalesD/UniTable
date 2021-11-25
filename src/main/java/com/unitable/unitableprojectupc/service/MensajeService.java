package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.dto.MensajeRequest;
import com.unitable.unitableprojectupc.dto.ReportMessageDto;
import com.unitable.unitableprojectupc.entities.Chat;
import com.unitable.unitableprojectupc.entities.Mensaje;
import com.unitable.unitableprojectupc.entities.Usuario;
import com.unitable.unitableprojectupc.repository.MensajeRepository;
import com.unitable.unitableprojectupc.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;
    @Autowired
    private ChatService chatService;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Mensaje createMensaje(MensajeRequest mensajeRequest) {
        Mensaje newMensaje = initMensaje(mensajeRequest);
        return newMensaje;
    }

    @Transactional(readOnly = true)
    public List<Mensaje> findAllMensajes() {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        return mensajes;
    }

    @Transactional
    public void deleteMensage(Mensaje mensaje){
        mensaje.getUsuario().getMensajes().remove(mensaje);
        mensajeRepository.delete(mensaje);
    }

    @Transactional(readOnly = true)
    public List<ReportMessageDto> reportMessageDtos(Long charId){
        List<ReportMessageDto> reports = new ArrayList<>();

        mensajeRepository.findMessagePerChat(charId).forEach( x -> {
            ReportMessageDto report = new ReportMessageDto();
            report.setQuatity(Integer.parseInt(String.valueOf(x[0])));
            report.setDateCreate(String.valueOf(x[1]));
            reports.add(report);
        });
        return reports;
    }

    private Mensaje initMensaje(MensajeRequest mensajeRequest) {
        Mensaje mensaje = new Mensaje();

        Usuario usuario = UserPrincipal.getCurrentUser();
        Chat chat = chatService.findChatById(mensajeRequest.getChat_id());

        mensaje.setMensaje(mensajeRequest.getMensaje());
        mensaje.setHora_mensaje(Time.valueOf(LocalTime.now()));
        
        mensaje.setChat(chat);
        mensaje.setUsuario(usuario);
        
        usuario.getMensajes().add(mensaje);
        chat.getMensajes().add(mensaje);
        chat.setCant_mensajes(chat.getCant_mensajes()+1);
        

        return mensaje;
    }
}
