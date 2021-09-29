package com.unitable.unitableprojectupc.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @OneToOne
    private Curso curso;

    @OneToOne
    private Chat chat;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<UsuarioGrupo> usuarioGrupos;

}