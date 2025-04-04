package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.PsicologoRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

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

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "foto")
    private String foto;

    @Column(name = "bio")
    private String bio;

    @Column(name = "formacao")
    private String formacao;

    @Column(name = "contato", nullable = false)
    private String contato;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(name = "valor_consulta", nullable = false)
    private Double valorConsulta;

    @Column(name = "aceita_beneficio")
    private Boolean aceitaBeneficio;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "psicologos_especialidades",
            joinColumns = @JoinColumn(name = "id_psicologo"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidade")
    )
    private List<Especialidade> especialidades;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "psicologos_abordagens",
            joinColumns = @JoinColumn(name = "id_psicologo"),
            inverseJoinColumns = @JoinColumn(name = "id_abordagem")
    )
    private List<Abordagem> abordagens;


    public Psicologo(PsicologoRequestDTO data, List<Especialidade> especialidades, List<Abordagem> abordagens) {
        this.crp = data.crp();
        this.nome = data.nome();
        this.email = data.email();
        this.foto = data.foto();
        this.bio = data.bio();
        this.formacao = data.formacao();
        this.contato = data.contato();
        this.senhaHash = data.senhaHash();
        this.valorConsulta = data.valorConsulta();
        this.aceitaBeneficio = data.aceitaBeneficio();
        this.modalidadeAtendimento = data.modalidadeAtendimento();

        if (data.endereco() != null) {
            this.endereco = new EnderecoPsicologo(data.endereco());
            this.endereco.setPsicologo(this);
        }

        this.especialidades = especialidades;
        this.abordagens = abordagens;
    }

}
