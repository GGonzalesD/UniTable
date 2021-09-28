package com.unitable.unitableprojectupc.service;

import com.unitable.unitableprojectupc.dto.RecompensaRequest;
import com.unitable.unitableprojectupc.entities.Recompensa;
import com.unitable.unitableprojectupc.repository.RecompensaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecompensaService {

    @Autowired
    private RecompensaRepository recompensaRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Recompensa createRecompensa(RecompensaRequest recompensaRequest) {
        Recompensa newRecompensa = initRecompensa(recompensaRequest);
        return recompensaRepository.save(newRecompensa);
    }

    @Transactional(readOnly = true)
    public List<Recompensa> findAllRecompensas() {
        List<Recompensa> recompensas = recompensaRepository.findAll();
        return recompensas;
    }

    private Recompensa initRecompensa(RecompensaRequest recompensaRequest) {
        Recompensa recompensa = new Recompensa();
        recompensa.setNombre(recompensaRequest.getNombre());
        recompensa.setDetalles(recompensaRequest.getDetalles());
        return recompensa;
    }
}
