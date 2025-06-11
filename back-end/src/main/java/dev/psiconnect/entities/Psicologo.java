package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.PsicologoRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * Entidade que representa um psicólogo no sistema.
 *
 * Contém informações pessoais, contato, dados profissionais,
 * endereço, especialidades, abordagens e preferências de atendimento.
 */
@Table(name = "psicologos")
@Entity(name = "psicologos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Psicologo {

    /**
     * Identificador único do psicólogo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Número do CRP (Conselho Regional de Psicologia), único e obrigatório.
     */
    @Column(name = "crp", unique = true, nullable = false)
    private String crp;

    /**
     * Nome completo do psicólogo.
     */
    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * Email do psicólogo, único e obrigatório.
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * URL ou caminho para a foto do psicólogo (opcional).
     */
    @Column(name = "foto")
    private String foto;

    /**
     * Biografia ou descrição do psicólogo (opcional).
     */
    @Column(name = "bio")
    private String bio;

    /**
     * Formação acadêmica ou profissional do psicólogo.
     */
    @Column(name = "formacao")
    private String formacao;

    /**
     * Contato telefônico ou outro meio de contato do psicólogo.
     */
    @Column(name = "contato", nullable = false)
    private String contato;

    /**
     * Hash da senha do psicólogo para autenticação.
     */
    @Column(name = "senha_hash", nullable = false)
    private String senha;

    /**
     * Valor cobrado por consulta pelo psicólogo.
     */
    @Column(name = "valor_consulta", nullable = false)
    private Double valorConsulta;

    /**
     * Indica se o psicólogo aceita pacientes com benefício social.
     */
    @Column(name = "aceita_beneficio")
    private Boolean aceitaBeneficio;

    /**
     * Modalidade de atendimento oferecida pelo psicólogo.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade_atendimento", nullable = false)
    private ModalidadeAtendimento modalidadeAtendimento;

    /**
     * Enumeração das modalidades de atendimento possíveis.
     */
    public enum ModalidadeAtendimento {
        REMOTO,
        PRESENCIAL,
        HIBRIDO;
    }

    /**
     * Endereço do psicólogo.
     * Relacionamento OneToOne com cascata ALL e remoção de órfãos.
     */
    @OneToOne(mappedBy = "psicologo", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnderecoPsicologo endereco;

    /**
     * Lista das especialidades do psicólogo.
     * Relacionamento ManyToMany com carregamento EAGER.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "psicologos_especialidades",
            joinColumns = @JoinColumn(name = "id_psicologo"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidade")
    )
    private List<Especialidade> especialidades;

    /**
     * Lista das abordagens do psicólogo.
     * Relacionamento ManyToMany com carregamento EAGER.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "psicologos_abordagens",
            joinColumns = @JoinColumn(name = "id_psicologo"),
            inverseJoinColumns = @JoinColumn(name = "id_abordagem")
    )
    private List<Abordagem> abordagens;

    /**
     * Construtor que inicializa um psicólogo a partir do DTO de requisição,
     * lista de especialidades e lista de abordagens.
     *
     * @param data DTO contendo os dados do psicólogo.
     * @param especialidades Lista das especialidades atribuídas.
     * @param abordagens Lista das abordagens atribuídas.
     */
    public Psicologo(PsicologoRequestDTO data, List<Especialidade> especialidades, List<Abordagem> abordagens) {
        this.crp = data.crp();
        this.nome = data.nome();
        this.email = data.email();
        this.foto = data.foto();
        this.bio = data.bio();
        this.formacao = data.formacao();
        this.contato = data.contato();
        this.senha = data.senha();
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
