package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.PsicologoRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "psicologos")
@Entity(name = "psicologos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Psicologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "crp", unique = true, nullable = false)
    private String crp;

    @Column(name = "nome_psicologo", nullable = false)
    private String nome;

    @Column(name = "email_psicologo", unique = true, nullable = false)
    private String email;

    @Column(name = "bio_psicologo")
    private String bio;

    @Column(name = "formacao_psicologo")
    private String formacao;

    @Column(name = "contato_psicologo", nullable = false)
    private String contato;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(name = "valor_padrao_consulta", nullable = false)
    private Double valorPadraoConsulta;

    @Column(name = "aceita_valor_social")
    private Boolean aceitaValorSocial;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade_atendimento", nullable = false)
    private ModalidadeAtendimento modalidadeAtendimento;

    public enum ModalidadeAtendimento {
        remoto,
        presencial,
        hibrido;
    }

    @OneToOne(mappedBy = "psicologo", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnderecoPsicologo endereco;

    public Psicologo(PsicologoRequestDTO data) {
        this.crp = data.crp();
        this.nome = data.nome();
        this.email = data.email();
        this.bio = data.bio();
        this.formacao = data.formacao();
        this.contato = data.contato();
        this.senhaHash = data.senhaHash();
        this.valorPadraoConsulta = data.valorPadraoConsulta();
        this.aceitaValorSocial = data.aceitaValorSocial();
        this.modalidadeAtendimento = data.modalidadeAtendimento();
        if (data.endereco() != null) {
            this.endereco = new EnderecoPsicologo(data.endereco());
            this.endereco.setPsicologo(this);
        }
    }
}
