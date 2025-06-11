package dev.psiconnect.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * Entidade que representa uma Abordagem psicológica utilizada pelos psicólogos no sistema PsiConnect.
 *
 * Cada abordagem possui um identificador único e um nome, que deve ser único no banco.
 *
 * Está relacionada a vários psicólogos através de uma associação ManyToMany, pois um psicólogo
 * pode ter múltiplas abordagens e uma abordagem pode ser usada por vários psicólogos.
 *
 * Esta classe é mapeada para a tabela "abordagens" no banco de dados.
 *
 * Lombok é utilizado para gerar getters, setters, construtores, equals e hashCode.
 */
@Entity
@Table(name = "abordagens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Abordagem {

    /**
     * Identificador único da abordagem.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da abordagem psicológica.
     * É obrigatório, não pode ser nulo e deve ser único no banco.
     */
    @Column(name = "nome_abordagem", nullable = false, unique = true)
    private String nome;

    /**
     * Lista de psicólogos que utilizam esta abordagem.
     * Relacionamento ManyToMany bidirecional mapeado pelo atributo "abordagens" na entidade Psicologo.
     */
    @ManyToMany(mappedBy = "abordagens")
    private List<Psicologo> psicologos;
}
