package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Abordagem;

/**
 * DTO de resposta que representa uma abordagem terapêutica.
 *
 * Usado para enviar dados da abordagem ao cliente da API.
 *
 * @param id    Identificador único da abordagem.
 * @param nome  Nome da abordagem terapêutica.
 */
public record AbordagemResponseDTO(
        Long id,
        String nome
) {

    /**
     * Construtor que cria um DTO a partir da entidade Abordagem.
     *
     * @param abordagem Entidade Abordagem para conversão.
     */
    public AbordagemResponseDTO(Abordagem abordagem) {
        this(
                abordagem.getId(),
                abordagem.getNome()
        );
    }
}
