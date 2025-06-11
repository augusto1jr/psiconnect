package dev.psiconnect.dtos.requests;

/**
 * DTO utilizado para receber dados de requisição relacionados a uma abordagem psicológica.
 *
 * <p>Atualmente utilizado principalmente para associar uma abordagem existente a um psicólogo.</p>
 *
 * @param id Identificador da abordagem.
 */
public record AbordagemRequestDTO(
        Long id
) {}
