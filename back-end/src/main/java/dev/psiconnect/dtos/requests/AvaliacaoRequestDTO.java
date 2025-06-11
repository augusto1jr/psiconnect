package dev.psiconnect.dtos.requests;

import java.time.LocalDateTime;

/**
 * DTO para requisição de criação ou atualização de uma avaliação.
 *
 * Este objeto carrega os dados necessários para registrar uma avaliação
 * feita por um paciente sobre uma consulta com um psicólogo.
 *
 * @param idConsulta     Identificador da consulta avaliada.
 * @param idPaciente     Identificador do paciente que realizou a avaliação.
 * @param idPsicologo    Identificador do psicólogo avaliado.
 * @param nota           Nota atribuída pelo paciente (por exemplo, de 1 a 5).
 * @param comentario     Comentário opcional sobre a avaliação.
 * @param dataAvaliacao  Data e hora em que a avaliação foi realizada.
 */
public record AvaliacaoRequestDTO(
        Long idConsulta,
        Long idPaciente,
        Long idPsicologo,
        Integer nota,
        String comentario,
        LocalDateTime dataAvaliacao
) {}
