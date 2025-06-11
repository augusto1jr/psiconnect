package dev.psiconnect.repositories;

import dev.psiconnect.entities.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade {@link Especialidade}.
 *
 * Fornece operações básicas de CRUD para especialidades dos psicólogos.
 */
@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
}
