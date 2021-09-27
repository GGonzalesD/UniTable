package com.unitable.unitableprojectupc.entities;

import com.unitable.unitableprojectupc.common.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @Column(name = "password")
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

}
