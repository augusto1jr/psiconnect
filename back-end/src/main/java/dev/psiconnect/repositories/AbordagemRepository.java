package dev.psiconnect.repositories;

import dev.psiconnect.entities.Abordagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade {@link Abordagem}.
 *
 * Fornece operações básicas de CRUD e consultas padrão para Abordagem.
 */
@Repository
public interface AbordagemRepository extends JpaRepository<Abordagem, Long> {
}
