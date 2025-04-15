package dev.psiconnect.dtos.requests;

import java.time.LocalDateTime;

public record AvaliacaoRequestDTO(
        Long idConsulta,
        Long idPaciente,
        Long idPsicologo,
        Integer nota,
        String comentario,
        LocalDateTime dataAvaliacao
) {}
