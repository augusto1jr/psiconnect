package dev.psiconnect.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * Entidade que representa uma especialidade que pode ser associada a psicólogos.
 *
 * Cada especialidade possui um nome único e pode estar associada a vários psicólogos.
 * O relacionamento com Psicologo é bidirecional ManyToMany,
 * onde o mapeamento é feito pelo atributo "especialidades" na entidade Psicologo.
 */
@Entity
@Table(name = "especialidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Especialidade {

    /**
     * Identificador único da especialidade.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da especialidade.
     * Campo obrigatório e único.
     */
    @Column(name = "nome_especialidade", nullable = false, unique = true)
    private String nome;

    /**
     * Lista de psicólogos que possuem esta especialidade.
     * Mapeamento ManyToMany bidirecional, sendo o lado inverso do relacionamento.
     */
    @ManyToMany(mappedBy = "especialidades")
    private List<Psicologo> psicologos;
}
