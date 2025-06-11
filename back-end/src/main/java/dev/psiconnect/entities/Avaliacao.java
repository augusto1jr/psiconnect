package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.AvaliacaoRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidade que representa uma Avaliação feita por um paciente a um psicólogo referente a uma consulta.
 *
 * Cada avaliação está vinculada a exatamente uma consulta (relação OneToOne) e referencia o paciente
 * e o psicólogo envolvidos (relacionamentos ManyToOne).
 *
 * A avaliação contém uma nota obrigatória, um comentário opcional e a data em que foi realizada.
 *
 * A data da avaliação é inicializada com o momento atual por padrão.
 *
 * A classe é mapeada para a tabela "avaliacoes" no banco de dados.
 *
 * Lombok é usado para gerar os getters, setters, construtores, equals e hashCode.
 */
@Table(name = "avaliacoes")
@Entity(name = "avaliacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Avaliacao {

    /**
     * Identificador único da avaliação.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Long id;

    /**
     * Consulta relacionada à avaliação.
     * Relação OneToOne, única e obrigatória.
     */
    @OneToOne
    @JoinColumn(name = "id_consulta", unique = true, nullable = false)
    private Consulta consulta;

    /**
     * Psicólogo avaliado.
     * Relação ManyToOne obrigatória.
     */
    @ManyToOne
    @JoinColumn(name = "id_psicologo", nullable = false)
    private Psicologo psicologo;

    /**
     * Paciente que realizou a avaliação.
     * Relação ManyToOne obrigatória.
     */
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    /**
     * Nota da avaliação, obrigatória.
     */
    @Column(name = "nota", nullable = false)
    private Integer nota;

    /**
     * Comentário opcional deixado pelo paciente na avaliação.
     */
    @Column(name = "comentario")
    private String comentario;

    /**
     * Data e hora em que a avaliação foi feita.
     * Inicializada por padrão com o momento atual.
     */
    @Column(name = "data_avaliacao")
    private LocalDateTime dataAvaliacao = LocalDateTime.now();

    /**
     * Construtor para criar uma avaliação a partir do DTO de requisição, associando a consulta, psicólogo e paciente.
     *
     * @param data DTO contendo os dados da avaliação
     * @param consulta Consulta associada
     * @param psicologo Psicólogo avaliado
     * @param paciente Paciente que realizou a avaliação
     */
    public Avaliacao(AvaliacaoRequestDTO data, Consulta consulta, Psicologo psicologo, Paciente paciente) {
        this.consulta = consulta;
        this.psicologo = psicologo;
        this.paciente = paciente;
        this.nota = data.nota();
        this.comentario = data.comentario();
        this.dataAvaliacao = data.dataAvaliacao();
    }
}
