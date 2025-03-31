package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.EnderecoPsiRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "enderecos_psicologos")
@Entity(name = "EnderecoPsicologo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "psicologo")
public class EnderecoPsicologo {

    @Id
    @Column(name = "id_psicologo")
    private Long idPsicologo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_psicologo")
    private Psicologo psicologo;

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

    public EnderecoPsicologo(EnderecoPsiRequestDTO data) {
        this.psicologo = psicologo;
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
