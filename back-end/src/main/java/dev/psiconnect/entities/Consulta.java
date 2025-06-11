package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.ConsultaRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma Consulta agendada entre um Paciente e um Psicólogo.
 *
 * Contém informações sobre data, status, modalidade, tipo e valor da consulta.
 * Utiliza enums para representar os valores de status, modalidade e tipo.
 *
 * Cada consulta está associada a exatamente um psicólogo e um paciente (ManyToOne).
 *
 * Lombok gera getters, setters, construtores, equals e hashCode automaticamente.
 */
@Table(name = "consultas")
@Entity(name = "consultas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    /**
     * Identificador único da consulta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long id;

    /**
     * Psicólogo responsável pela consulta.
     * Relação ManyToOne, obrigatória.
     */
    @ManyToOne
    @JoinColumn(name = "id_psicologo", nullable = false)
    private Psicologo psicologo;

    /**
     * Paciente que realizou a consulta.
     * Relação ManyToOne, obrigatória.
     */
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    /**
     * Data e hora agendada para a consulta.
     * Campo obrigatório.
     */
    @Column(name = "data_consulta", nullable = false)
    private LocalDateTime dataConsulta;

    /**
     * Status da consulta, que pode ser:
     * - AGENDADA: Consulta marcada e pendente.
     * - CONCLUIDA: Consulta realizada.
     * - CANCELADA: Consulta cancelada.
     *
     * Utiliza enum Status armazenado como String no banco.
     * Valor padrão é AGENDADA.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.AGENDADA;

    /**
     * Enum que define os possíveis status da consulta.
     */
    public enum Status {
        AGENDADA,
        CONCLUIDA,
        CANCELADA
    }

    /**
     * Modalidade da consulta, que pode ser:
     * - REMOTA: Consulta realizada remotamente.
     * - PRESENCIAL: Consulta realizada presencialmente.
     *
     * Campo obrigatório, armazenado como String no banco.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade", nullable = false)
    private Modalidade modalidade;

    /**
     * Enum que define as modalidades possíveis da consulta.
     */
    public enum Modalidade {
        REMOTA,
        PRESENCIAL
    }

    /**
     * Tipo da consulta, que pode ser:
     * - COMUM: Consulta regular.
     * - SOCIAL: Consulta com benefício social.
     *
     * Campo obrigatório, armazenado como String no banco.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;

    /**
     * Enum que define os tipos possíveis da consulta.
     */
    public enum Tipo {
        COMUM,
        SOCIAL
    }

    /**
     * Valor cobrado pela consulta.
     * Campo obrigatório.
     */
    @Column(nullable = false)
    private BigDecimal valor;

    /**
     * Construtor que cria uma consulta a partir dos dados recebidos no DTO de requisição,
     * vinculando o psicólogo e paciente.
     *
     * @param data DTO contendo os dados da consulta
     * @param psicologo Psicólogo responsável pela consulta
     * @param paciente Paciente que realizou a consulta
     */
    public Consulta(ConsultaRequestDTO data, Psicologo psicologo, Paciente paciente) {
        this.psicologo = psicologo;
        this.paciente = paciente;
        this.dataConsulta = data.dataConsulta();
        this.status = data.status();
        this.modalidade = data.modalidade();
        this.tipo = data.tipo();
        this.valor = data.valor();
    }
}
