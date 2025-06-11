package dev.psiconnect.repositories;

import dev.psiconnect.entities.EnderecoPsicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade {@link EnderecoPsicologo}.
 *
 * Fornece operações básicas de CRUD para os endereços dos psicólogos.
 */
@Repository
public interface EnderecoPsiRepository extends JpaRepository<EnderecoPsicologo, Long> {
}
