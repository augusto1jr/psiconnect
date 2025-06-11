package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.PacienteRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * Entidade que representa um paciente no sistema.
 *
 * Cada paciente possui dados pessoais, informações de contato,
 * preferências por especialidades e abordagens, além de endereço e benefício social.
 */
@Table(name = "pacientes")
@Entity(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    /**
     * Identificador único do paciente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * CPF do paciente, único e obrigatório.
     */
    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    /**
     * Nome completo do paciente.
     */
    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * Email do paciente, único e obrigatório.
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * URL ou caminho para a foto do paciente (opcional).
     */
    @Column(name = "foto")
    private String foto;

    /**
     * Biografia ou descrição do paciente (opcional).
     */
    @Column(name = "bio")
    private String bio;

    /**
     * Contato telefônico ou outro meio de contato do paciente.
     */
    @Column(name = "contato", nullable = false)
    private String contato;

    /**
     * Hash da senha do paciente para autenticação.
     */
    @Column(name = "senha_hash", nullable = false)
    private String senha;

    /**
     * Tipo de benefício social do paciente.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "beneficio_social", nullable = false)
    private BeneficioSocial beneficioSocial;

    /**
     * Enumeração que representa os tipos possíveis de benefício social.
     */
    public enum BeneficioSocial {
        NENHUM,
        ESTUDANTE,
        CADUNICO;
    }

    /**
     * Endereço do paciente.
     * Relacionamento OneToOne, cascata ALL para persistência e remoção automática,
     * e orphanRemoval para garantir a exclusão do endereço ao remover o paciente.
     */
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnderecoPaciente endereco;

    /**
     * Lista das especialidades preferidas pelo paciente.
     * Relacionamento ManyToMany com carregamento EAGER.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "preferencias_especialidades",
            joinColumns = @JoinColumn(name = "id_paciente"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidade")
    )
    private List<Especialidade> prefEspecialidades;

    /**
     * Lista das abordagens preferidas pelo paciente.
     * Relacionamento ManyToMany com carregamento EAGER.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "preferencias_abordagens",
            joinColumns = @JoinColumn(name = "id_paciente"),
            inverseJoinColumns = @JoinColumn(name = "id_abordagem")
    )
    private List<Abordagem> prefAbordagens;

    /**
     * Construtor que inicializa um paciente a partir do DTO de requisição e listas de preferências.
     *
     * @param data Dados recebidos no DTO para criação do paciente.
     * @param prefEspecialidades Lista das especialidades preferidas.
     * @param prefAbordagens Lista das abordagens preferidas.
     */
    public Paciente(PacienteRequestDTO data, List<Especialidade> prefEspecialidades, List<Abordagem> prefAbordagens) {
        this.cpf = data.cpf();
        this.nome = data.nome();
        this.email = data.email();
        this.foto = data.foto();
        this.bio = data.bio();
        this.contato = data.contato();
        this.senha = data.senha();
        this.beneficioSocial = data.beneficioSocial();

        if (data.endereco() != null) {
            this.endereco = new EnderecoPaciente(data.endereco());
            this.endereco.setPaciente(this);
        }

        this.prefEspecialidades = prefEspecialidades;
        this.prefAbordagens = prefAbordagens;
    }

}
