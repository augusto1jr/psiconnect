package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.PacienteRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Table(name = "pacientes")
@Entity(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "foto")
    private String foto;

    @Column(name = "bio")
    private String bio;

    @Column(name = "contato", nullable = false)
    private String contato;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "beneficio_social", nullable = false)
    private BeneficioSocial beneficioSocial;

    public enum BeneficioSocial {
        NENHUM,
        ESTUDANTE,
        CADUNICO;
    }

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnderecoPaciente endereco;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "preferencias_especialidades",
            joinColumns = @JoinColumn(name = "id_paciente"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidade")
    )
    private List<Especialidade> prefEspecialidades;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "preferencias_abordagens",
            joinColumns = @JoinColumn(name = "id_paciente"),
            inverseJoinColumns = @JoinColumn(name = "id_abordagem")
    )
    private List<Abordagem> prefAbordagens;

    public Paciente(PacienteRequestDTO data, List<Especialidade> prefEspecialidades, List<Abordagem> prefAbordagens) {
        this.cpf = data.cpf();
        this.nome = data.nome();
        this.email = data.email();
        this.foto = data.foto();
        this.bio = data.bio();
        this.contato = data.contato();
        this.senhaHash = data.senhaHash();
        this.beneficioSocial = data.beneficioSocial();

        if (data.endereco() != null) {
            this.endereco = new EnderecoPaciente(data.endereco());
            this.endereco.setPaciente(this);
        }

        this.prefEspecialidades = prefEspecialidades;
        this.prefAbordagens = prefAbordagens;
    }

}
