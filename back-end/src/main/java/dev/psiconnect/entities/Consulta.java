package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.ConsultaRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "consultas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_psicologo", nullable = false)
    private Psicologo psicologo;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @Column(name = "data_consulta", nullable = false)
    private LocalDateTime dataConsulta;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.agendada;

    public enum Status {
        agendada,
        concluida,
        cancelada
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade", nullable = false)
    private Modalidade modalidade;

    public enum Modalidade {
        remota,
        presencial
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;

    public enum Tipo {
        comum,
        social
    }

    @Column(nullable = false)
    private BigDecimal valor;


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
