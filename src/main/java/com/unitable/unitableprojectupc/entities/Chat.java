package com.unitable.unitableprojectupc.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cant_mensajes")
    private Integer cant_mensajes;

	@Column(name = "detalles")
    private String detalles;

    @OneToMany(
        mappedBy = "chat",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Mensaje> mensajes;

    @OneToOne(mappedBy = "chat")
    private Grupo grupo;
}
