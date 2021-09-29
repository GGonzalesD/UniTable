package com.unitable.unitableprojectupc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "detalles")
    private String detalles;

    @Column(name = "fecha_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_ini;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_fin;

    @Column(name = "duracion_min")
    private Integer duracion_min;

    @Column(name = "activa")
    private Boolean activa;
}
