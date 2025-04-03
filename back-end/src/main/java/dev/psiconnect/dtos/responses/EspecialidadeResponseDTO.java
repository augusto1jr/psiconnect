package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Especialidade;

public record EspecialidadeResponseDTO(
        Long id,
        String nome
) {

    public EspecialidadeResponseDTO(Especialidade especialidade) {
        this(
                especialidade.getId(),
                especialidade.getNome()
        );
    }
}
