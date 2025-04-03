package dev.psiconnect.repositories;

import dev.psiconnect.entities.Abordagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbordagemRepository extends JpaRepository<Abordagem, Long> {
}

