package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Consulta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsultaResponseDTO(
        Long id,
        Long idPaciente,
        String nomePaciente,
        Long idPsicologo,
        String nomePsicologo,
        LocalDateTime dataConsulta,
        String status,
        String modalidade,
        String tipo,
        BigDecimal valor
) {
    public ConsultaResponseDTO(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getPaciente().getId(),
                consulta.getPaciente().getNome(),
                consulta.getPsicologo().getId(),
                consulta.getPsicologo().getNome(),
                consulta.getDataConsulta(),
                consulta.getStatus().name(),
                consulta.getModalidade().name(),
                consulta.getTipo().name(),
                consulta.getValor()
        );
    }
}
