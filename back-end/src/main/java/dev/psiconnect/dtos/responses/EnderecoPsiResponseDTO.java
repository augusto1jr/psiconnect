package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.EnderecoPsicologo;

/**
 * DTO de resposta que representa o endereço profissional ou residencial de um psicólogo.
 *
 * Contém informações completas do endereço e coordenadas geográficas.
 *
 * @param idPsicologo Identificador do psicólogo ao qual o endereço pertence.
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
public record EnderecoPsiResponseDTO(
        Long idPsicologo,
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
     * Construtor que cria um DTO a partir da entidade EnderecoPsicologo.
     *
     * @param endereco Entidade EnderecoPsicologo para conversão em DTO.
     */
    public EnderecoPsiResponseDTO(EnderecoPsicologo endereco) {
        this(
                endereco.getIdPsicologo(),
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
