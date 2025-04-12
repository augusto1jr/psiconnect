package dev.psiconnect.repositories;

import dev.psiconnect.entities.EnderecoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoPacRepository extends JpaRepository<EnderecoPaciente, Long> {
}
