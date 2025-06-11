package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Avaliacao;
import java.time.LocalDateTime;

/**
 * DTO de resposta que representa uma avaliação feita por um paciente a um psicólogo.
 *
 * Fornece informações detalhadas da avaliação, incluindo nomes e IDs dos envolvidos,
 * nota, comentário e data da avaliação.
 *
 * @param idConsulta     Identificador da consulta avaliada.
 * @param idPaciente     Identificador do paciente que realizou a avaliação.
 * @param nomePaciente   Nome completo do paciente.
 * @param idPsicologo    Identificador do psicólogo avaliado.
 * @param nomePsicologo  Nome completo do psicólogo.
 * @param nota           Nota atribuída pelo paciente.
 * @param comentario     Comentário feito pelo paciente na avaliação.
 * @param dataAvaliacao  Data e hora em que a avaliação foi realizada.
 */
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
    /**
     * Construtor que cria um DTO a partir da entidade Avaliacao.
     *
     * @param avaliacao Entidade Avaliacao para conversão em DTO.
     */
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
