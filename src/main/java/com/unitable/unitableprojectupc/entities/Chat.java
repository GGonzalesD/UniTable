package com.unitable.unitableprojectupc.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private List<Mensaje> mensajes;

    @OneToOne(mappedBy = "chat")
    private Grupo grupo;
}
