package dev.psiconnect.dtos.requests;

/**
 * DTO para requisição de cadastro ou atualização do endereço do paciente.
 *
 * Contém informações detalhadas sobre o endereço residencial do paciente,
 * incluindo localização geográfica.
 *
 * @param rua         Nome da rua ou logradouro.
 * @param numero      Número do imóvel.
 * @param complemento Informação adicional do endereço (ex: apartamento, bloco).
 * @param bairro      Bairro onde o paciente reside.
 * @param cidade      Cidade do endereço.
 * @param estado      Estado ou unidade federativa.
 * @param cep         Código postal (CEP) do endereço.
 * @param latitude    Coordenada geográfica de latitude.
 * @param longitude   Coordenada geográfica de longitude.
 */
public record EnderecoPacRequestDTO(
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
