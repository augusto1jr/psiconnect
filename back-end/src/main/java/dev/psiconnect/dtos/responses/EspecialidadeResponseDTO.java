package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Especialidade;

/**
 * DTO de resposta que representa uma especialidade psicológica.
 *
 * Utilizado para enviar informações sobre especialidades aos clientes da API.
 *
 * @param id    Identificador único da especialidade.
 * @param nome  Nome da especialidade.
 */
public record EspecialidadeResponseDTO(
        Long id,
        String nome
) {

    /**
     * Construtor que cria um DTO a partir da entidade Especialidade.
     *
     * @param especialidade Entidade Especialidade para conversão em DTO.
     */
    public EspecialidadeResponseDTO(Especialidade especialidade) {
        this(
                especialidade.getId(),
                especialidade.getNome()
        );
    }
}
