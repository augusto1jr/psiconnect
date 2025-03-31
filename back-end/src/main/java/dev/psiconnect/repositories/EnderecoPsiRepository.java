package dev.psiconnect.repositories;

import dev.psiconnect.entities.EnderecoPsicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoPsiRepository extends JpaRepository<EnderecoPsicologo, Long> {
}
