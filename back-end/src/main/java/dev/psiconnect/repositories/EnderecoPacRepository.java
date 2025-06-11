package dev.psiconnect.repositories;

import dev.psiconnect.entities.EnderecoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade {@link EnderecoPaciente}.
 *
 * Fornece operações básicas de CRUD para os endereços dos pacientes.
 */
@Repository
public interface EnderecoPacRepository extends JpaRepository<EnderecoPaciente, Long> {
}
