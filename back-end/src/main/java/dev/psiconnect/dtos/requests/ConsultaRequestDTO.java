package dev.psiconnect.dtos.requests;

import dev.psiconnect.entities.Consulta.Status;
import dev.psiconnect.entities.Consulta.Modalidade;
import dev.psiconnect.entities.Consulta.Tipo;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para requisição de criação ou atualização de uma consulta.
 *
 * Este objeto contém os dados necessários para agendar, modificar ou
 * registrar uma consulta entre paciente e psicólogo.
 *
 * @param idPaciente    Identificador do paciente que realizará a consulta.
 * @param idPsicologo   Identificador do psicólogo responsável pela consulta.
 * @param dataConsulta  Data e horário previstos para a realização da consulta.
 * @param status        Status atual da consulta (ex: agendada, concluída, cancelada).
 * @param modalidade   Modalidade da consulta (ex: remota ou presencial).
 * @param tipo         Tipo da consulta (ex: comum ou social).
 * @param valor        Valor cobrado pela consulta.
 */
public record ConsultaRequestDTO(
        Long idPaciente,
        Long idPsicologo,
        LocalDateTime dataConsulta,
        Status status,
        Modalidade modalidade,
        Tipo tipo,
        BigDecimal valor
) {}
