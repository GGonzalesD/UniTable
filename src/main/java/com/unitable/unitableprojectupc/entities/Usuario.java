package com.unitable.unitableprojectupc.entities;

import com.unitable.unitableprojectupc.common.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "correo")
    private String correo;

    @Column(name = "password",length = 150,nullable = false)
    private String password;

    @Column(name = "carrera")
    private String carrera;

    @Column(name = "num_act_completas")
    private Integer num_act_completas;

    @Column(name = "num_monedas")
    private Integer num_monedas;

    @Column(name = "isPremium")
    private Boolean isPremium;

    @Column(name = "tipo_usuario")
    @Enumerated(value = EnumType.STRING)
    private UserType tipo_usuario;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Recompensa> recompensas;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Actividad> actividades;
    
    @OneToMany(
        mappedBy = "usuario",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private List<Mensaje> mensajes;

    @ManyToMany
    @JoinTable(name = "usuario_grupo",
        joinColumns = @JoinColumn(name = "user_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "grupo_id", nullable = false)
    )
    private List<Grupo> grupos;

    @ManyToMany
    @JoinTable(name = "usuario_contactos",
        joinColumns = @JoinColumn(name = "user_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "contacto_id", nullable = false)
    )
    private List<Usuario> contactos;

}
