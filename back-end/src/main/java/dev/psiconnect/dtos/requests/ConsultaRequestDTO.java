package dev.psiconnect.dtos.requests;

import dev.psiconnect.entities.Consulta.Status;
import dev.psiconnect.entities.Consulta.Modalidade;
import dev.psiconnect.entities.Consulta.Tipo;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsultaRequestDTO(
        Long idPaciente,
        Long idPsicologo,
        LocalDateTime dataConsulta,
        Status status, // "agendada", "concluida", "cancelada"
        Modalidade modalidade, // "remota", "presencial"
        Tipo tipo, // "comum", "social"
        BigDecimal valor
) {}
