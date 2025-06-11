package dev.psiconnect.dtos.requests;

/**
 * DTO para requisição de cadastro ou atualização do endereço do psicólogo.
 *
 * Contém informações detalhadas sobre o endereço profissional ou residencial
 * do psicólogo, incluindo localização geográfica.
 *
 * @param rua         Nome da rua ou logradouro.
 * @param numero      Número do imóvel.
 * @param complemento Informação adicional do endereço (ex: sala, andar).
 * @param bairro      Bairro onde o psicólogo está localizado.
 * @param cidade      Cidade do endereço.
 * @param estado      Estado ou unidade federativa.
 * @param cep         Código postal (CEP) do endereço.
 * @param latitude    Coordenada geográfica de latitude.
 * @param longitude   Coordenada geográfica de longitude.
 */
public record EnderecoPsiRequestDTO(
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Double latitude,
        Double longitude
) {}
