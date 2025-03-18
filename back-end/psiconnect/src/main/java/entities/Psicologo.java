package entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "psicologos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "formacao")
    private String formacao;

    @Column(name = "contato", nullable = false)
    private String contato;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(name = "valor_padrao_consulta", nullable = false)
    private Double valorPadraoConsulta;

    @Column(name = "aceita_valor_social")
    private Boolean aceitaValorSocial;

    @Enumerated(EnumType.STRING) // Garante que o valor será armazenado como texto no banco
    @Column(name = "modalidade_atendimento", nullable = false)
    private ModalidadeAtendimento modalidadeAtendimento;

    public enum ModalidadeAtendimento {
        REMOTO,
        PRESENCIAL,
        HIBRIDO;
    }
}



