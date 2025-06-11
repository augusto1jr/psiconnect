package dev.psiconnect.repositories;

import dev.psiconnect.entities.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório Spring Data JPA para a entidade {@link Avaliacao}.
 *
 * Fornece operações básicas de CRUD e consultas específicas para avaliações.
 */
@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    /**
     * Busca todas as avaliações associadas a um psicólogo pelo seu ID.
     *
     * @param id ID do psicólogo.
     * @return Lista de avaliações do psicólogo.
     */
    List<Avaliacao> findByPsicologoId(Long id);

    /**
     * Busca uma avaliação única associada a uma consulta pelo ID da consulta.
     *
     * @param consultaId ID da consulta.
     * @return Avaliação opcional vinculada à consulta, se existir.
     */
    Optional<Avaliacao> findByConsultaId(Long consultaId);

}
