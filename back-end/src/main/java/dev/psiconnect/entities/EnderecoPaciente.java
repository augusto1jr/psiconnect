package dev.psiconnect.entities;

import dev.psiconnect.dtos.requests.EnderecoPacRequestDTO;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade que representa o endereço associado a um Paciente.
 *
 * Utiliza o ID do paciente como chave primária compartilhada (shared primary key).
 * Relacionamento OneToOne bidirecional com a entidade Paciente.
 *
 * Contém campos detalhados para localização e endereço do paciente,
 * incluindo latitude e longitude para localização geográfica.
 *
 * Lombok é usado para gerar getters, setters, construtores,
 * equals e hashCode com base no paciente (chave primária).
 */
@Table(name = "enderecos_pacientes")
@Entity(name = "EnderecoPaciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "paciente")
public class EnderecoPaciente {

    /**
     * Identificador do paciente, que é também a chave primária desta entidade.
     */
    @Id
    @Column(name = "id_paciente")
    private Long idPaciente;

    /**
     * Relação OneToOne com a entidade Paciente.
     * Utiliza a mesma chave primária via @MapsId.
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

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
     * @param data DTO contendo os dados do endereço do paciente.
     */
    public EnderecoPaciente(EnderecoPacRequestDTO data) {
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
