package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.EnderecoPaciente;

/**
 * DTO de resposta que representa o endereço residencial de um paciente.
 *
 * Contém detalhes completos do endereço, incluindo localização geográfica.
 *
 * @param idPaciente  Identificador do paciente ao qual o endereço pertence.
 * @param rua         Nome da rua ou logradouro.
 * @param numero      Número do imóvel.
 * @param complemento Informação adicional do endereço (ex: apartamento, bloco).
 * @param bairro      Bairro do endereço.
 * @param cidade      Cidade do endereço.
 * @param estado      Estado ou unidade federativa.
 * @param cep         Código postal (CEP).
 * @param latitude    Coordenada geográfica de latitude.
 * @param longitude   Coordenada geográfica de longitude.
 */
public record EnderecoPacResponseDTO(
        Long idPaciente,
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Double latitude,
        Double longitude
) {
    /**
     * Construtor que cria um DTO a partir da entidade EnderecoPaciente.
     *
     * @param endereco Entidade EnderecoPaciente para conversão em DTO.
     */
    public EnderecoPacResponseDTO(EnderecoPaciente endereco) {
        this(
                endereco.getIdPaciente(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getLatitude(),
                endereco.getLongitude()
        );
    }
}
