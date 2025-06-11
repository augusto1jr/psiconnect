package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.EnderecoPsiRequestDTO;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade que representa o endereço associado a um Psicólogo.
 *
 * Utiliza o ID do psicólogo como chave primária compartilhada (shared primary key).
 * Relacionamento OneToOne bidirecional com a entidade Psicologo.
 *
 * Contém campos detalhados para localização e endereço do psicólogo,
 * incluindo latitude e longitude para localização geográfica.
 *
 * Lombok é usado para gerar getters, setters, construtores,
 * equals e hashCode com base no psicólogo (chave primária).
 */
@Table(name = "enderecos_psicologos")
@Entity(name = "EnderecoPsicologo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "psicologo")
public class EnderecoPsicologo {

    /**
     * Identificador do psicólogo, que é também a chave primária desta entidade.
     */
    @Id
    @Column(name = "id_psicologo")
    private Long idPsicologo;

    /**
     * Relação OneToOne com a entidade Psicologo.
     * Utiliza a mesma chave primária via @MapsId.
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "id_psicologo")
    private Psicologo psicologo;

    /**
     * Nome da rua do endereço.
     * Campo obrigatório.
     */
    @Column(nullable = false)
    private String rua;

    /**
     * Número do endereço.
     * Campo obrigatório.
     */
    @Column(nullable = false)
    private String numero;

    /**
     * Complemento do endereço (opcional).
     */
    private String complemento;

    /**
     * Bairro do endereço.
     * Campo obrigatório.
     */
    @Column(nullable = false)
    private String bairro;

    /**
     * Cidade do endereço.
     * Campo obrigatório.
     */
    @Column(nullable = false)
    private String cidade;

    /**
     * Estado do endereço.
     * Campo obrigatório.
     */
    @Column(nullable = false)
    private String estado;

    /**
     * CEP (código postal) do endereço.
     * Campo obrigatório.
     */
    @Column(nullable = false)
    private String cep;

    /**
     * Latitude geográfica do endereço.
     * Campo obrigatório.
     */
    @Column(nullable = false)
    private Double latitude;

    /**
     * Longitude geográfica do endereço.
     * Campo obrigatório.
     */
    @Column(nullable = false)
    private Double longitude;

    /**
     * Construtor que cria um endereço a partir dos dados recebidos no DTO de requisição.
     *
     * @param data DTO contendo os dados do endereço do psicólogo.
     */
    public EnderecoPsicologo(EnderecoPsiRequestDTO data) {
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
