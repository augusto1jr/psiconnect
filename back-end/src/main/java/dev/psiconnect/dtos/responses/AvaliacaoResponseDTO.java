package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Avaliacao;

import java.time.LocalDateTime;

public record AvaliacaoResponseDTO(
        Long idConsulta,
        Long idPaciente,
        String nomePaciente,
        Long idPsicologo,
        String nomePsicologo,
        Integer nota,
        String comentario,
        LocalDateTime dataAvaliacao
) {
    public AvaliacaoResponseDTO(Avaliacao avaliacao) {
        this(
                avaliacao.getId(),
                avaliacao.getPaciente().getId(),
                avaliacao.getPaciente().getNome(),
                avaliacao.getPsicologo().getId(),
                avaliacao.getPsicologo().getNome(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getDataAvaliacao()
        );
    }
}
