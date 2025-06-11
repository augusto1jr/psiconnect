package dev.psiconnect.repositories;

import dev.psiconnect.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade {@link Paciente}.
 *
 * Fornece operações básicas de CRUD para pacientes,
 * além de um método para buscar paciente pelo e-mail.
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    /**
     * Busca um paciente pelo seu e-mail.
     *
     * @param email o e-mail do paciente
     * @return o paciente encontrado, ou null se nenhum paciente possuir o e-mail informado
     */
    Paciente findByEmail(String email);
}
