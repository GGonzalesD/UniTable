package com.unitable.unitableprojectupc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario_grupo")
public class UsuarioGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "num_usuarios")
    private Integer num_usuarios;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Grupo grupo;
}
