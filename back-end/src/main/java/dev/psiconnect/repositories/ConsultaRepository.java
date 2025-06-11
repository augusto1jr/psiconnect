package dev.psiconnect.repositories;

import dev.psiconnect.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório Spring Data JPA para a entidade {@link Consulta}.
 *
 * Fornece operações básicas de CRUD e consultas específicas para consultas realizadas.
 */
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    /**
     * Busca todas as consultas associadas a um psicólogo pelo seu ID.
     *
     * @param idPsicologo ID do psicólogo.
     * @return Lista de consultas do psicólogo.
     */
    List<Consulta> findByPsicologoId(Long idPsicologo);

    /**
     * Busca as consultas de um psicólogo por ID e filtradas por status.
     *
     * @param id ID do psicólogo.
     * @param status Status da consulta (AGENDADA, CONCLUIDA, CANCELADA).
     * @return Lista de consultas do psicólogo com o status especificado.
     */
    List<Consulta> findByPsicologoIdAndStatus(Long id, Consulta.Status status);
}
