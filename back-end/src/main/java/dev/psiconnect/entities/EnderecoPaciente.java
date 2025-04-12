package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.EnderecoPacRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "enderecos_pacientes")
@Entity(name = "EnderecoPaciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "paciente")
public class EnderecoPaciente {

    @Id
    @Column(name = "id_paciente")
    private Long idPaciente;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String numero;

    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    public EnderecoPaciente(EnderecoPacRequestDTO data) {
        this.paciente = paciente;
        this.rua = data.rua();
        this.numero = data.numero();
        this.complemento = data.complemento();
        this.bairro = data.bairro();
        this.cidade = data.cidade();
        this.estado = data.estado();
        this.cep = data.cep();
        this.latitude = data.latitude();
        this.longitude = data.longitude();
    }
}
