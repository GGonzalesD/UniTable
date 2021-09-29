package com.unitable.unitableprojectupc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "hora_mensaje")
    private Time hora_mensaje;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Chat chat;
}
