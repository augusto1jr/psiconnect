package dev.psiconnect.dtos.requests;

/**
 * DTO para requisição que envolve uma especialidade.
 *
 * Usado para identificar uma especialidade pelo seu ID, por exemplo,
 * para associar a especialidade a um psicólogo ou paciente.
 *
 * @param id Identificador único da especialidade.
 */
public record EspecialidadeRequestDTO(
        Long id
) {}
