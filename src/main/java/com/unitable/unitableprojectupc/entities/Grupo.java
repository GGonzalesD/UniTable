package com.unitable.unitableprojectupc.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "grupos")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tema")
    private String tema;

    @Column(name = "descripcion")
    private String descripcion;

    @Column (name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_creacion;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_fin;

    @ManyToOne
    private Curso curso;

    @OneToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @ManyToMany(mappedBy = "grupos")
    private List<Usuario> usuarios;

}