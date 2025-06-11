package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Consulta;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de resposta que representa os dados de uma consulta entre paciente e psicólogo.
 *
 * Fornece informações detalhadas da consulta, incluindo identificação, nomes,
 * data, status, modalidade, tipo e valor cobrado.
 *
 * @param id             Identificador único da consulta.
 * @param idPaciente     Identificador do paciente.
 * @param nomePaciente   Nome completo do paciente.
 * @param idPsicologo    Identificador do psicólogo.
 * @param nomePsicologo  Nome completo do psicólogo.
 * @param dataConsulta   Data e horário previstos para a consulta.
 * @param status         Status atual da consulta (ex: AGENDADA, CONCLUIDA, CANCELADA).
 * @param modalidade     Modalidade da consulta (ex: REMOTA, PRESENCIAL).
 * @param tipo           Tipo da consulta (ex: COMUM, SOCIAL).
 * @param valor          Valor cobrado pela consulta.
 */
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
    /**
     * Construtor que cria um DTO a partir da entidade Consulta.
     *
     * @param consulta Entidade Consulta para conversão em DTO.
     */
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
