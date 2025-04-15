package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.AvaliacaoRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "avaliacoes")
@Entity(name = "avaliacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_consulta", unique = true, nullable = false)
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "id_psicologo", nullable = false)
    private Psicologo psicologo;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @Column(name = "nota", nullable = false)
    private Integer nota;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "data_avaliacao")
    private LocalDateTime dataAvaliacao = LocalDateTime.now();

    public Avaliacao(AvaliacaoRequestDTO data, Consulta consulta, Psicologo psicologo, Paciente paciente) {
        this.consulta = consulta;
        this.psicologo = psicologo;
        this.paciente = paciente;
        this.nota = data.nota();
        this.comentario = data.comentario();
        this.dataAvaliacao = data.dataAvaliacao();
    }
}
