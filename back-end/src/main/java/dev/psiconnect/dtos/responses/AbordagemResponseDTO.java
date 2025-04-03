package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Abordagem;

public record AbordagemResponseDTO(
        Long id,
        String nome
) {

    public AbordagemResponseDTO(Abordagem abordagem) {
        this(
                abordagem.getId(),
                abordagem.getNome()
        );
    }
}
